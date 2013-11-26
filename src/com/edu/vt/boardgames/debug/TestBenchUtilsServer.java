package edu.vt.boardgames.debug;

import vt.team9.customgames.Board;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import edu.vt.boardgames.network.UtilsServer;

public class TestBenchUtilsServer
{
	private static final String KEY_RESPONSE = "response_key";
	private static final String KEY_MSG_TYPE = "msg_Type";
	private static final short MSG_TYPE_RESPONSE_POST_BOARD = 1;
	private static final short MSG_TYPE_RESPONSE_GET_BOARD = 2;

	private static Context s_context;

	public static void testPostBoard(final Context context, Board board)
	{
		UtilsServer.submitMoveToServer(s_handler, KEY_MSG_TYPE, MSG_TYPE_RESPONSE_POST_BOARD,
				KEY_RESPONSE, board);
		s_context = context;
	}

	public static void testGetBoards(final Context context)
	{
		UtilsServer.getBoardFromServer(s_handler, KEY_MSG_TYPE, MSG_TYPE_RESPONSE_GET_BOARD,
				KEY_RESPONSE);
		s_context = context;
	}

	private static Handler s_handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);

			Bundle msgBundle = msg.getData();

			switch (msgBundle.getShort(KEY_MSG_TYPE))
			{
			case MSG_TYPE_RESPONSE_POST_BOARD:
				String postResponse = msgBundle.getString(KEY_RESPONSE);
				if (postResponse != null)
				{
					Toast.makeText(s_context, "Post response: " + postResponse, Toast.LENGTH_LONG)
							.show();
				}
				else
				{
					Toast.makeText(s_context, "Received null post response", Toast.LENGTH_LONG)
							.show();
				}
				break;
			case MSG_TYPE_RESPONSE_GET_BOARD:
				String getResponse = msgBundle.getString(KEY_RESPONSE);
				if (getResponse != null)
				{
					Toast.makeText(s_context, "Post response: " + getResponse, Toast.LENGTH_LONG)
							.show();
				}
				else
				{
					Toast.makeText(s_context, "Received null post response", Toast.LENGTH_LONG)
							.show();
				}
				break;
			default:
				MyLogger.logWarning(TestBenchUtilsServer.class.getName(),
						"UI_Handler handleMessage",
						"MainActivity Handler received non-supported message type");
				break;
			}
		}
	};
}
