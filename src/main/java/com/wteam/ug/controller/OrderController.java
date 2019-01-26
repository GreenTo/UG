package com.wteam.ug.controller;

import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.payUtils;
import com.alipay.demo.trade.utils.Utils;
import com.wteam.ug.entity.Order;
import com.wteam.ug.service.OrderService;
import com.wteam.ug.service.StatisticsService;
import com.wteam.ug.wrap.JsonResult;
import com.wteam.ug.wrap.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
@Api(value = "工单相关接口")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    StatisticsService statisticsService;

    private static Log log = LogFactory.getLog(OrderController.class);

    @ApiOperation(value = "新建工单")
    @PostMapping("/add")
    public JsonResult add(@RequestParam long addressId, @RequestParam long businessScopeId,
                          @RequestParam long customerId, @RequestParam String description,
                          @RequestParam String remark, @RequestParam Date time,
                          @RequestParam MultipartFile[] files){
        Order order = new Order();
        order.setAddressId(addressId);
        order.setBussinessScopeId(businessScopeId);
        order.setCustomerId(customerId);
        order.setDescription(description);
        order.setRemark(remark);
        order.setTime(time);
        boolean b = orderService.add(order, files);
        if (b) {

            try {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                statisticsService.updateWorkOrders(date.parse(date.format(new Date())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return ResultFactory.ok();
        }
        else
            return ResultFactory.bad();
    }

    @PostMapping("/cancel")
    @ApiOperation(value = "取消工单")
    public JsonResult cancel(@RequestParam long id){
        boolean b = orderService.cancel(id);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/comment")
    @ApiOperation(value = "评价工单")
    public JsonResult comment(@RequestParam long employeeId,@RequestParam long orderId,@RequestParam double evaluate){
        boolean b = orderService.comment(employeeId,evaluate,orderId);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改工单相关信息")
    public JsonResult update(@RequestBody Order order){
        boolean b = orderService.update(order);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/assign")
    @ApiOperation(value = "分派工单")
    public JsonResult assign(@RequestParam long orderId,@RequestParam long companyId){
        boolean b = orderService.assgin(orderId,companyId);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/distribute")
    @ApiOperation(value = "分配工单")
    public JsonResult distribute(@RequestParam long orderId,@RequestParam long employeeId){
        boolean b = orderService.distribute(orderId,employeeId);
        if (b)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @PostMapping("/pay")
    @ApiOperation(value = "工单付款")
    public JsonResult pay(@RequestParam long customerId,@RequestParam long orderId){
        String url = orderService.pay(customerId, orderId);
        if (url != null)
            return ResultFactory.ok();
        else
            return ResultFactory.bad();
    }

    @GetMapping("/findByCustomerId")
    @ApiOperation(value = "根据客户id查找工单")
    public JsonResult findByCustomerId(@RequestParam long id){
        List<Order> orderList = orderService.findByCustomerId(id);
        return ResultFactory.ok(orderList);
    }

    @GetMapping("/findByFirstCategory")
    @ApiOperation(value = "根据一级类目查找工单")
    public JsonResult findByFirstCategory(@RequestParam String firstCategory,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size){
        Page<Order> orderPage = orderService.findByFirstCategory(firstCategory, page, size);
        return ResultFactory.ok(orderPage);
    }

    @GetMapping("/findByStatus")
    @ApiOperation(value = "根据状态id查找工单")
    public JsonResult findByStatus(@RequestParam Integer id,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size){
        Page<Order> orderPage = orderService.findByStatus(id, page, size);
        return ResultFactory.ok(orderPage);
    }

    @GetMapping("/findByStatusAndFirstCategoryAndPayment")
    @ApiOperation(value = "根据一级类目和状态id和支付方式(可选)查找工单")
    public JsonResult findByStatusAndFirstCategoryAndPayment(@RequestParam Integer id,
                                                             @RequestParam String firstCategory,
                                                             @RequestParam(defaultValue = "") String payment,
                                                             @RequestParam(defaultValue = "1") Integer page,
                                                             @RequestParam(defaultValue = "10") Integer size){
        Page<Order> orderPage = orderService.findByStatusAndFirstCategoryAndPayment(id, firstCategory, payment, page, size);
        return ResultFactory.ok(orderPage);
    }

    @GetMapping("/search")
    @ApiOperation(value = "模糊搜索")
    public JsonResult search(@RequestParam String param,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size){
        if (param == null || param.trim().equals("")){
            return ResultFactory.bad();
        }else {
            Page<Order> orderPage = orderService.search(param, page, size);
            return ResultFactory.ok(orderPage);
        }
    }

    @GetMapping("/findByOutTradeNo")
    @ApiOperation(value = "根据商品订单id查询是否交易成功")
    public JsonResult findByTradeNo(@RequestParam String tradeNo){
        AlipayF2FQueryResult result = payUtils.trade_query(tradeNo);
        String msg = null;
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("查询返回该订单支付成功: )");

                AlipayTradeQueryResponse response = result.getResponse();
                payUtils.dumpResponse(response);

                log.info(response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        log.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
                return ResultFactory.ok(response);

            case FAILED:
                msg = "查询返回该订单支付失败或被关闭!!!";
                log.error(msg);
                break;

            case UNKNOWN:
                msg = "系统异常，订单支付状态未知!!!";
                log.error(msg);
                break;

            default:
                msg = "不支持的交易状态，交易返回异常!!!";
                log.error(msg);
                break;
        }
        return ResultFactory.erro(msg);
    }

    @GetMapping("/findByCustomerAndStatus")
    @ApiOperation(value = "根据客户id和状态id查询订单")
    public JsonResult findByCustomerAndStatus(@RequestParam long customerId,long statusId){
        List<Order> orderList = orderService.findByCustomerAndStatus(customerId, statusId);
        return ResultFactory.ok(orderList);
    }

}
