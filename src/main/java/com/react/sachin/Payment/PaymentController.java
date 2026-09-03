package com.react.sachin.Payment;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import com.ccavenue.security.AesCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private TempOrderRepository tempOrderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SequenceService sequenceService;

    @Value("${ccavenue.working-key}")
    private String workingKey;

    @Value("${ccavenue.merchant-id}")
    private String merchantId;

    @Value("${ccavenue.access-code}")
    private String accessCode;

    @Value("${ccavenue.url}")
    private String ccavenueUrl;

    @PostMapping("/initiate")
    public ResponseEntity<?> initiate(@RequestBody PaymentRequest request) {
System.out.println("in payment process-------");
        TempOrder temp = new TempOrder();
        
        String orderId = sequenceService.merchantOrderNo("EXAM_FEE", "SK", "N");
        
        //temp.setOrderId(request.getOrderId());
        temp.setOrderId(orderId);
        temp.setAmount(request.getAmount());
        temp.setStatus("PENDING");
        temp.setUsername(request.getUsername());
        temp.setCreatedAt(LocalDateTime.now());
        tempOrderRepository.save(temp);

        // String merchantData = "merchant_id=" + merchantId
        //         + "&order_id=" + request.getOrderId()
        //         + "&amount=" + request.getAmount()
        //         + "&currency=INR"
        //         + "&redirect_url=http://localhost:7979/payment/response"
        //         + "&cancel_url=http://localhost:7979/payment/response"
        //         + "&language=EN";

        // ===== ONLY LINE THAT NEEDS THE REAL SIGNATURE =====
LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    map.put("merchant_id", merchantId);
	map.put("order_id", orderId);
	map.put("currency", "INR");
	map.put("amount", request.getAmount());  
	//map.put("redirect_url", "http://localhost:7979/payment/response");
	//map.put("cancel_url","http://localhost:7979/payment/response");
	map.put("redirect_url", "https://dee-color-midlands-sons.trycloudflare.com/payment/response");// allow third party api
    map.put("cancel_url", "https://dee-color-midlands-sons.trycloudflare.com/payment/response"); // allow third party api
    map.put("language", "EN");
      

        String ccaRequest="";
for (Map.Entry<String, String> entry : map.entrySet()) { 
	 ccaRequest = ccaRequest + entry.getKey() + "=" + entry.getValue() + "&";
 }

        AesCryptUtil aesUtil=new AesCryptUtil(workingKey);

        String encRequest = aesUtil.encrypt(ccaRequest);
        // =====================================================

        Map<String, String> response = new HashMap<>();
        response.put("encRequest", encRequest);
        response.put("accessCode", accessCode);
        response.put("ccavenueUrl", ccavenueUrl);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/response")
    public void handleResponse(@RequestParam("encResp") String encResp, 
                                 HttpServletResponse httpResponse) throws IOException {
System.out.println("in response....");
        // ===== ONLY LINE THAT NEEDS THE REAL SIGNATURE =====
        AesCryptUtil aesUtil=new AesCryptUtil(workingKey);
        String decryptedResponse = aesUtil.decrypt(encResp);
        //String decryptedResponse = AesCryptUtil.decrypt(encResp, workingKey);
        // =====================================================

        Map<String, String> params = parseCCAvenueResponse(decryptedResponse);

        String orderId = params.get("order_id");
        String orderStatus = params.get("order_status");
        String trackingId = params.get("tracking_id");
        String paymentMode = params.get("payment_mode");

        Optional<TempOrder> tempOptional = tempOrderRepository.findByOrderId(orderId);
System.out.println("----------------------tempOptional "+tempOptional);
        System.out.println("---------------------------------------kasdkaskdsakdkjsahdkj");
        if (tempOptional.isPresent()) {
            TempOrder temp = tempOptional.get();

            System.out.println("----------------------aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            if ("Success".equalsIgnoreCase(orderStatus)) {
             System.out.println("in response.. in sucesss..");
             
                Order order = new Order();
                order.setOrderId(temp.getOrderId());
                order.setAmount(temp.getAmount());
                order.setStatus("SUCCESS");
                order.setUsername(temp.getUsername());
                order.setTrackingId(trackingId);
                order.setPaymentMode(paymentMode);
                order.setCompletedAt(LocalDateTime.now());
                orderRepository.save(order);

                temp.setStatus(orderStatus == null ? "FAILED" : orderStatus.toUpperCase());
                tempOrderRepository.save(temp);
                //tempOrderRepository.delete(temp);
            } else {
                temp.setStatus(orderStatus == null ? "FAILED" : orderStatus.toUpperCase());
                tempOrderRepository.save(temp);
            }
        }

        String redirectUrl = "Success".equalsIgnoreCase(orderStatus)
                ? "http://localhost:5173/payment-success?orderId=" + orderId
                : "http://localhost:5173/payment-failed?orderId=" + orderId;
System.out.println("Redirecting to: " + redirectUrl);

        httpResponse.sendRedirect(redirectUrl);
    }

    private Map<String, String> parseCCAvenueResponse(String decryptedResponse) {
        Map<String, String> map = new HashMap<>();
        for (String pair : decryptedResponse.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                map.put(kv[0], kv[1]);
            }
        }
        return map;
    }

 @GetMapping("/order-status")
public ResponseEntity<?> getOrderStatus(@RequestParam("orderId") String orderId) {
    Optional<Order> orderOptional = orderRepository.findByOrderId(orderId);

    if (orderOptional.isPresent()) {
        return ResponseEntity.ok(orderOptional.get());
    }

    Optional<TempOrder> tempOptional = tempOrderRepository.findByOrderId(orderId);
    if (tempOptional.isPresent()) {
        return ResponseEntity.ok(tempOptional.get());
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
}   
}