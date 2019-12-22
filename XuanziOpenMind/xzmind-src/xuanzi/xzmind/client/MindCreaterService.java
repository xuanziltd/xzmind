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

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

import sbaike.client.h5.client.Action;
import sbaike.client.h5.client.ElUtils;
import xuanzi.h5.fs.client.PopupMenu;
import xuanzi.h5.fs.core.Log;

public class MindCreaterService {

	XZMindEditor editor;
	
	public MindCreaterService(XZMindEditor editor) {
		this.editor = editor;
	}
	
	public  final Action printAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) { 
			final ElUtils printEl = ElUtils.bind(RootPanel.getBodyElement()).createEl("iframe").attr("src",ExportDocumentService.makeURL(editor.getRenderedText()+"<script>print()</script>")).addClass("fs-print-frame");
			Timer timer = new Timer() {
				
				@Override
				public void run() { 
					Log.log("remove print iframe");
					printEl.remove();
				}
			};
			timer.schedule(8000);
		}
	};
	
	private Action createMindAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) { 
			PopupMenu pm = new SamplePopupMenu(editor,event); 
			pm.show();
		}
	};

	private Action insertQRCrodeAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) {
			editor.insertMdText("###插入二维码\r\n"
					+ "```qrcode\r\n"  
					+"二维码码内容,链接 https://xuanzi.ltd\r\n"
					+"```\r\n" );
		}
	};

	public void onReady() { 
		editor.getToolbarEl().createButton("思维导图").addClass("fa fa-plus").click(createMindAction );
		editor.getToolbarEl().createButton("").addClass("fa fa-qrcode").click(insertQRCrodeAction  );
	}
}
