package com.example.rajeevnagarwal.dtc;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.Log;
import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Payment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Payment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Payment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	public Payment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment Payment.
	 */
	// TODO: Rename and change types and number of parameters
	public static Payment newInstance(String param1, String param2) {
		Payment fragment = new Payment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		final View view1 = inflater.inflate(R.layout.fragment_payment, container, false);

		/*
		*********************************************************************
		*********************************************************************************
		*/

		Button pay_btn = (Button)view1.findViewById(R.id.pay_button);
		pay_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {



				PaytmPGService Service = PaytmPGService.getStagingService();
				Map<String, String> paramMap = new HashMap<String, String>();

				// these are mandatory parameters

				Random r = new Random(System.currentTimeMillis());
				final String orderId = "ORDER" + (1 + r.nextInt(2)) * 10000
						+ r.nextInt(10000);

                final String cust_id="CUST"+Main.androidId;

				paramMap.put("ORDER_ID", orderId );
				paramMap.put("MID", "WorldP64425807474247");
				paramMap.put("CUST_ID", cust_id);
				paramMap.put("CHANNEL_ID", "WAP");
				paramMap.put("INDUSTRY_TYPE_ID", "Retail");
				paramMap.put("WEBSITE", "worldpressplg");
				paramMap.put("TXN_AMOUNT", "1");
				paramMap.put("THEME", "merchant");
				//paramMap.put("EMAIL", ((EditText) findViewById(R.id.cust_email_id)).getText().toString());
				if(Main.mobileNumber!=null || !Main.mobileNumber.equals(""))
					paramMap.put("MOBILE_NO", Main.mobileNumber);
				PaytmOrder Order = new PaytmOrder(paramMap);

				PaytmMerchant Merchant = new PaytmMerchant(
						"https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
						"https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");

				Service.initialize(Order, Merchant, null);

				Service.startPaymentTransaction(view1.getContext(), true, true,
						new PaytmPaymentTransactionCallback() {
							@Override
							public void someUIErrorOccurred(String inErrorMessage) {
								// Some UI Error Occurred in Payment Gateway Activity.
								// // This may be due to initialization of views in
								// Payment Gateway Activity or may be due to //
								// initialization of webview. // Error Message details
								// the error occurred.
							}

							@Override
							public void onTransactionSuccess(Bundle inResponse) {
								// After successful transaction this method gets called.
								// // Response bundle contains the merchant response
								// parameters.
								Log.d("LOG", "Payment Transaction is successful " + inResponse);
								Toast.makeText(view1.getContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");
                                String timeStamp = dateFormat.format(new Date());

                                File root = android.os.Environment.getExternalStorageDirectory();
                                File file=new File(root.getAbsolutePath() ,/*time.format3339(false)*/"Download/"+timeStamp+" "+orderId+".png");


                                //Find screen size
                                WindowManager manager = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE));
                                Display display = manager.getDefaultDisplay();
                                Point point = new Point();
                                display.getSize(point);
                                int width = point.x;
                                int height = point.y;
                                int smallerDimension = width < height ? width : height;
                                smallerDimension = smallerDimension * 3/4;

                                //Encode with a QR Code image
                                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(orderId,
                                        null,
                                        Contents.Type.TEXT,
                                        BarcodeFormat.QR_CODE.toString(),
                                        smallerDimension);
                                try {

                                    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();

                                    FileOutputStream out = null;
                                    try {
                                        out = new FileOutputStream(file);
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                                        // PNG is a lossless format, the compression factor (100) is ignored

                                        //Show the Dialoge for Image
                                        final Dialog settingsDialog = new Dialog(getContext());
                                        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                        View qrView = inflater.inflate(R.layout.fragment_image__qr__dialog, null);
                                        settingsDialog.setContentView(qrView);

                                        Button okbtn = (Button) qrView.findViewById(R.id.okQRcode);
                                        okbtn.setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                settingsDialog.dismiss();
                                            }
                                        });

                                        TextView qrtext = (TextView) qrView.findViewById(R.id.textQR);
                                        qrtext.setText("Ticke Location : Download/"+timeStamp+".png");

                                        ImageView myImage = (ImageView) qrView.findViewById(R.id.qr_image_id);
                                        myImage.setImageBitmap(bitmap);


                                        settingsDialog.show();


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            if (out != null) {
                                                out.close();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                } catch (WriterException e) {
                                    e.printStackTrace();
                                }



							}

							@Override
							public void onTransactionFailure(String inErrorMessage,
															 Bundle inResponse) {
								// This method gets called if transaction failed. //
								// Here in this case transaction is completed, but with
								// a failure. // Error Message describes the reason for
								// failure. // Response bundle contains the merchant
								// response parameters.
								Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
								Toast.makeText(view1.getContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
							}

							@Override
							public void networkNotAvailable() { // If network is not
								// available, then this
								// method gets called.
							}

							@Override
							public void clientAuthenticationFailed(String inErrorMessage) {
								// This method gets called if client authentication
								// failed. // Failure may be due to following reasons //
								// 1. Server error or downtime. // 2. Server unable to
								// generate checksum or checksum response is not in
								// proper format. // 3. Server failed to authenticate
								// that client. That is value of payt_STATUS is 2. //
								// Error Message describes the reason for failure.
							}

							@Override
							public void onErrorLoadingWebPage(int iniErrorCode,
															  String inErrorMessage, String inFailingUrl) {

							}

							// had to be added: NOTE
							@Override
							public void onBackPressedCancelTransaction() {
                                // TODO Auto-generated method stub


                                Log.d("LOG", "Payment Transaction Back / Cencel");
                                Toast.makeText(view1.getContext(), "Payment Transaction Back ", Toast.LENGTH_LONG).show();

                                Time time = new Time();
                                time.setToNow();
                                Log.d("TIME TEST", Long.toString(time.toMillis(false)));




							}



						});



			}
		});
		return view1;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnFragmentInteractionListener) {
			mListener = (OnFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p/>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		void onFragmentInteraction(Uri uri);
	}
	public void onPayClick()
	{


	}
}
