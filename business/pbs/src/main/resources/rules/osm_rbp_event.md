# 总体结构

    type: String
    body: Object
    source: String
    timestamp: Long
    
## 1. 支付

    body {
    	orderNum : String,
    	orderDetailNum : String,
    	
    	totalPrice : BigDecimal,
    	paymentId : String , // 大订单支付款ID
    }
    
## 2. 退款

    body {
    	orderNum : String,
    	orderDetailNum : String,
    	
    	totalPrice : BigDecimal,
    	paymentId : String , // 大订单支付款ID
    	desc:string//退款描述
    }
    
    
## 3. 返回数据
    
    body {
    	orderNum : String,
    	orderDetailNum : String,
    	
    	paymentId : String,
    }
    
    