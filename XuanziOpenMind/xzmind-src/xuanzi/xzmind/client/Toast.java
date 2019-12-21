/*
   Copyright (c) 2019 Shanghai Xuanzi Technology Co. Ltd https://xuanzi.ltd
   XZMind is licensed under the Mulan PSL v1.
   You can use this software according to the terms and conditions of the Mulan PSL v1.
   You may obtain a copy of Mulan PSL v1 at:
      http://license.coscl.org.cn/MulanPSL
   THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
   PURPOSE.
   See the Mulan PSL v1 for more details.

*/


package xuanzi.xzmind.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

import sbaike.client.h5.client.ElUtils;


/**
 * 提示框组件
 * 
 * @author 彭立铭
 *
 */
public class Toast {

	public Toast() {
		// TODO Auto-generated constructor stub
	}

	String text;
	
	public static Toast loading() {
		text("加载中 ..").show();
		return toast;
	}
	
	static Toast toast;
	
	ElUtils el;
	
	public static Toast text(String text_) {
		if(toast==null)
			toast = new Toast();
		toast.text = text_;
		return toast;
	}
	
	public  void show() {
		el = ElUtils.bind(RootPanel.getBodyElement()).createDiv().addClass("fs-toast");
		el.createLabel(text);
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				el.remove();
			}
		};
		timer.schedule(1300);
	}
	
}
