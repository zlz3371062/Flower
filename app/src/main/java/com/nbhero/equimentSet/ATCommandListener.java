package com.nbhero.equimentSet;

import com.nbhero.util.NetworkProtocol;

public interface ATCommandListener {

	public void onEnterCMDMode(boolean success);
	public void onExitCMDMode(boolean success, NetworkProtocol protocol);
	public void onSendFile(boolean success);
	public void onReload(boolean success);
	public void onReset(boolean success);
	public void onResponse(String response,String text);
	public void onResponseOfSendFile(String response);
}
