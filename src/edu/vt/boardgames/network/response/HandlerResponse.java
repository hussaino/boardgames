package edu.vt.boardgames.network.response;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HandlerResponse<T> extends Handler
{
	private static String keyResp = "response";

	@SuppressWarnings("unchecked")
	@Override
	public void handleMessage(Message msg)
	{
		super.handleMessage(msg);

		Bundle msgBundle = msg.getData();
		ArrayList<T> response = (ArrayList<T>) msgBundle.getSerializable(keyResp);
		onResponseArrayObj(response);
	}

	public void onResponseArrayObj(ArrayList<T> response)
	{
	}

}
