//package com.example.customer.message;
//
//import com.example.customer.model.entity.Customer;
//import com.example.customer.model.entity.CustomerSale;
//import com.example.customer.service.CustomerSaleService;
//import com.example.customer.service.CustomerService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageConsumer {
//    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);
//
//    private final String TOPIC_UPDATE_SALE_INFO ="topic_update_sale";
//
//    private final String TOPIC_REGISTER_NEW_CUSTOMER ="topic_register_new_customer";
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    CustomerSaleService customerSaleService;
//
//    @Autowired
//    CustomerService customerService;
//
//    @KafkaListener(topics = TOPIC_UPDATE_SALE_INFO)
//    void consumeMessageOnUpdateSaleInfo(String message){
//
//        try {
//            CustomerSale sale = objectMapper.readValue(message, CustomerSale.class);
//
//            customerSaleService.updateTotalSale(sale);
//
//        } catch (JsonProcessingException e) {
//            logger.error(e.getMessage(),e);
//        }
//
//    }
//
//    @KafkaListener(topics = TOPIC_REGISTER_NEW_CUSTOMER)
//    void consumeMessageOnRegisterNewCustomer(String message){
//
//        try {
//            Customer customer = objectMapper.readValue(message, Customer.class);
//
//            customerService.registerCustomer(
//                    customer);
//
//        } catch (JsonProcessingException e) {
//            logger.error(e.getMessage(),e);
//        }
//
//    }
//}
