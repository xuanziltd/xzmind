/*
   Copyright (c) 2019 Shanghai Xuanzi Technology Co. Ltd https://xuanzi.ltd
   IndexeddbFS is licensed under the Mulan PSL v1.
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import sbaike.client.h5.client.Action;
import sbaike.client.h5.client.ElUtils;
import xuanzi.h5.fs.client.PopupMenu;

/**
 * 导出服务类
 * 
 * @author 彭立铭
 *
 */
public class ExportDocumentService {
	
	protected  final Action reviewHTMLAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) { 
			ElUtils printEl = ElUtils.bind(RootPanel.getBodyElement()).createEl("iframe").attr("src",makeURL(editor.getRenderedText()+"<script>print()</script>")).addClass("fs-popup-frame");
			
		}
	};

	XZMindEditor editor;
	
	protected Action downloadMdAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) {
			download(editor.getMdText(), editor.getCurrentFile().getName());
		}
	};
	
	protected Action downloadHTMLAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) {
			
			download(editor.getRenderedText(), editor.getCurrentFile().getName()+".html");
		}
	};

	public ExportDocumentService(XZMindEditor editor) {
		this.editor = editor;
	}
	
	public static native String makeURL(String text)/*-{ 
	  return 'data:text/html;charset=utf-8,' + (encodeURIComponent(text)); 
	}-*/;

	
	public native void download(String text,String name)/*-{
		 var element = $wnd.document.createElement('a');
		  element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
		  element.setAttribute('download', name); 
		  element.style.display = 'none';
		  document.body.appendChild(element); 
		  element.click(); 
		  document.body.removeChild(element); 
	}-*/;
	
	
	public Action downloadAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) {
			PopupMenu pm = new PopupMenu(event);
			pm.addMenuItem("MD源文", downloadMdAction).className="fa fa-file-code-o";
			pm.addMenuItem("渲染文档", downloadHTMLAction ).className="fa fa-file-word-o";
			//pm.addMenuItem("预览文档", reviewHTMLAction ).className="fa fa-file-word-o";
			pm.show();
		}
	};

}
