package com.foodpurby.screens;

import java.io.Serializable;

public class ResultDelegate implements com.ipay.IpayResultDelegate, Serializable {

	private static final long serialVersionUID = 10001L;
	
	@Override
	public void onPaymentSucceeded(String TransId, String RefNo,
                                   String Amount, String Remark, String AuthCode) {
		
		TabaocoCreditActivity.resultTitle	= "SUCCESS";
		TabaocoCreditActivity.resultCode	= "1";
		TabaocoCreditActivity.resultInfo 	= "You have successfully completed your transaction.";
		
		String extra = "";
		extra=extra+"TransId\t= "+TransId+"\n";
		extra=extra+"RefNo\t\t= "+RefNo+"\n";
		extra=extra+"Amount\t= "+Amount+"\n";
		extra=extra+"Remark\t= "+Remark+"\n";
		extra=extra+"AuthCode\t= "+AuthCode;

		TabaocoCreditActivity.TransId=TransId;
		TabaocoCreditActivity.RefNo=RefNo;
		TabaocoCreditActivity.Amount=Amount;
		TabaocoCreditActivity.Remark=Remark;
		TabaocoCreditActivity.AuthCode=AuthCode;

		TabaocoCreditActivity.resultExtra = extra;
		
	}
	
	@Override
	public void onPaymentFailed(String TransId, String RefNo, String Amount,
                                String Remark, String ErrDesc) {

		TabaocoCreditActivity.resultTitle	= "FAILURE";
		TabaocoCreditActivity.resultCode	= "0";
		TabaocoCreditActivity.resultInfo 	= ErrDesc;
		
		String extra = "";
		extra=extra+"TransId\t= "+TransId+"\n";
		extra=extra+"RefNo\t\t= "+RefNo+"\n";
		extra=extra+"Amount\t= "+Amount+"\n";
		extra=extra+"Remark\t= "+Remark+"\n";
		extra=extra+"ErrDesc\t= "+ErrDesc;

		TabaocoCreditActivity.TransId=TransId;
		TabaocoCreditActivity.RefNo=RefNo;
		TabaocoCreditActivity.Amount=Amount;
		TabaocoCreditActivity.Remark=Remark;
		TabaocoCreditActivity.ErrDesc=ErrDesc;

		TabaocoCreditActivity.resultExtra = extra;
		
	}
	
	@Override
	public void onPaymentCanceled(String TransId, String RefNo, String Amount,
                                  String Remark, String ErrDesc) {

		TabaocoCreditActivity.resultTitle	= "CANCELED";
		TabaocoCreditActivity.resultCode	= "0";
		TabaocoCreditActivity.resultInfo 	= "The transaction has been cancelled.";
		
		String extra = "";
		extra=extra+"TransId\t= "+TransId+"\n";
		extra=extra+"RefNo\t\t= "+RefNo+"\n";
		extra=extra+"Amount\t= "+Amount+"\n";
		extra=extra+"Remark\t= "+Remark+"\n";
		extra=extra+"ErrDesc\t= "+ErrDesc;

		TabaocoCreditActivity.TransId=TransId;
		TabaocoCreditActivity.RefNo=RefNo;
		TabaocoCreditActivity.Amount=Amount;
		TabaocoCreditActivity.Remark=Remark;
		TabaocoCreditActivity.ErrDesc=ErrDesc;

		TabaocoCreditActivity.resultExtra = extra;
		
	}

	@Override
	public void onRequeryResult(String MerchantCode, String RefNo,
                                String Amount, String Result) {
		TabaocoCreditActivity.resultTitle	= "Requery Result";
		TabaocoCreditActivity.resultInfo 	= "";
		
		String extra = "";
		extra=extra+"MerchantCode\t= "+MerchantCode+"\n";
		extra=extra+"RefNo\t\t= "+RefNo+"\n";
		extra=extra+"Amount\t= "+Amount+"\n";
		extra=extra+"Result\t= "+Result;
		TabaocoCreditActivity.resultExtra = extra;
		
	}

}