package com.foodpurby.screens;

import java.io.Serializable;

public class PaymentResultDelegate implements com.ipay.IpayResultDelegate, Serializable {

	private static final long serialVersionUID = 10001L;
	
	@Override
	public void onPaymentSucceeded(String TransId, String RefNo,
                                   String Amount, String Remark, String AuthCode) {
		
		MyPaymentMethod.resultTitle	= "SUCCESS";
		MyPaymentMethod.resultCode	= "1";
		MyPaymentMethod.resultInfo 	= "You have successfully completed your transaction.";
		
		String extra = "";
		extra=extra+"TransId\t= "+TransId+"\n";
		extra=extra+"RefNo\t\t= "+RefNo+"\n";
		extra=extra+"Amount\t= "+Amount+"\n";
		extra=extra+"Remark\t= "+Remark+"\n";
		extra=extra+"AuthCode\t= "+AuthCode;

		MyPaymentMethod.TransId=TransId;
		MyPaymentMethod.RefNo=RefNo;
		MyPaymentMethod.Amount=Amount;
		MyPaymentMethod.Remark=Remark;
		MyPaymentMethod.AuthCode=AuthCode;

		MyPaymentMethod.resultExtra = extra;
		
	}
	
	@Override
	public void onPaymentFailed(String TransId, String RefNo, String Amount,
                                String Remark, String ErrDesc) {

		MyPaymentMethod.resultTitle	= "FAILURE";
		MyPaymentMethod.resultCode	= "0";
		MyPaymentMethod.resultInfo 	= ErrDesc;
		
		String extra = "";
		extra=extra+"TransId\t= "+TransId+"\n";
		extra=extra+"RefNo\t\t= "+RefNo+"\n";
		extra=extra+"Amount\t= "+Amount+"\n";
		extra=extra+"Remark\t= "+Remark+"\n";
		extra=extra+"ErrDesc\t= "+ErrDesc;

		MyPaymentMethod.TransId=TransId;
		MyPaymentMethod.RefNo=RefNo;
		MyPaymentMethod.Amount=Amount;
		MyPaymentMethod.Remark=Remark;
		MyPaymentMethod.ErrDesc=ErrDesc;

		MyPaymentMethod.resultExtra = extra;
		
	}
	
	@Override
	public void onPaymentCanceled(String TransId, String RefNo, String Amount,
                                  String Remark, String ErrDesc) {

		MyPaymentMethod.resultTitle	= "CANCELED";
		MyPaymentMethod.resultCode	= "0";
		MyPaymentMethod.resultInfo 	= "The transaction has been cancelled.";
		
		String extra = "";
		extra=extra+"TransId\t= "+TransId+"\n";
		extra=extra+"RefNo\t\t= "+RefNo+"\n";
		extra=extra+"Amount\t= "+Amount+"\n";
		extra=extra+"Remark\t= "+Remark+"\n";
		extra=extra+"ErrDesc\t= "+ErrDesc;

		MyPaymentMethod.TransId=TransId;
		MyPaymentMethod.RefNo=RefNo;
		MyPaymentMethod.Amount=Amount;
		MyPaymentMethod.Remark=Remark;
		MyPaymentMethod.ErrDesc=ErrDesc;

		MyPaymentMethod.resultExtra = extra;
		
	}

	@Override
	public void onRequeryResult(String MerchantCode, String RefNo,
                                String Amount, String Result) {
		MyPaymentMethod.resultTitle	= "Requery Result";
		MyPaymentMethod.resultInfo 	= "";
		
		String extra = "";
		extra=extra+"MerchantCode\t= "+MerchantCode+"\n";
		extra=extra+"RefNo\t\t= "+RefNo+"\n";
		extra=extra+"Amount\t= "+Amount+"\n";
		extra=extra+"Result\t= "+Result;
		MyPaymentMethod.resultExtra = extra;
		
	}

}