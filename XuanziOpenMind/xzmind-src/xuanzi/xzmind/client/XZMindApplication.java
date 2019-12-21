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
 
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import sbaike.client.h5.client.ElUtils;
import sbaike.client.h5.client.LocalStorage;
import xuanzi.h5.fs.core.IFile;
import xuanzi.h5.fs.core.Log;
import xuanzi.h5.fs.core.Result;
import xuanzi.h5.fs.impl.FileSystem;
 
/**
 * 应用入口类
 * 
 * @author 彭立铭
 *
 */
public class XZMindApplication   implements EntryPoint{

	public XZMindApplication() { 
		
	}
	
	FileSystem fs ;

	@Override
	public void onModuleLoad() {
		String theme = LocalStorage.get("fs-theme");
		if(theme!=null)
			RootPanel.getBodyElement().addClassName(theme);
		fs = new FileSystem("WebFileService") {
			
			@Override
			protected void onReady() {
				Log.log("onReady");
				parseParams();
			}
		};  
	}

	String folder;
	
	/**
	 * 解析入口参数
	 */
	protected void parseParams() {
		 folder = "/项目库";
		String filter = "#indexed://";
		int pos = Window.Location.getHref().indexOf(filter);
		if(pos>0) { 
				folder = URL.decodeQueryString(Window.Location.getHref().substring(pos+filter.length())); 
		}
		fs.goFolder(folder,true, new Result() {
			
			@Override
			public void result(IFile file) {
				if(file==null||file.isFile()) {
					initEditor(folder,file);
				}else {
					initFileManager(folder);
				}
				
			}
		});
	}

	/**
	 * 初始化文件管理器
	 * @param folder
	 */
	private void initFileManager(String folder) { 
		ElUtils bodyEl = ElUtils.bind(RootPanel.getBodyElement()); 
		XZFileManager manger = new XZFileManager(bodyEl.createDiv().toElement(),fs,folder);
		
	}

	/**
	 * 初始化文档编辑器
	 * @param folder
	 * @param file
	 */
	private void initEditor(String folder,IFile file) {
		 
		ElUtils parent = ElUtils.bind(RootPanel.getBodyElement()).createDiv();
		Document.get().getElementById("gitee_icon").removeFromParent();
		
		XZMindEditor editor = new XZMindEditor(parent.toElement(),fs,file,folder);
	}
	
	

}
