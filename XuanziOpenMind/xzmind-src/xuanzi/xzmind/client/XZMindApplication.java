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
import xuanzi.h5.fs.core.FileInfo;
import xuanzi.h5.fs.core.IFile;
import xuanzi.h5.fs.core.Log;
import xuanzi.h5.fs.core.Result;
import xuanzi.h5.fs.impl.FileSystem;
 

public class XZMindApplication   implements EntryPoint{

	public XZMindApplication() {
		// TODO Auto-generated constructor stub
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

	private void initFileManager(String folder) { 
		ElUtils bodyEl = ElUtils.bind(RootPanel.getBodyElement()); 
		XZFileManager manger = new XZFileManager(bodyEl.createDiv().toElement(),fs,folder);
		
	}

	private void initEditor(String folder,IFile file) {
		// TODO Auto-generated method stub
		/*
		Node root = new Node("DDDDD");
		Scene scene = new WestScene();
		root.setScene(scene);
		root.setShape(new Round());
		root.setLine(new Line());
		for(int i=0;i<5;i++) {
			Node node = root.addNode("DDDD "+i);
			for(int x=0;x<3;x++) {
				node.addNode("Node "+x);
			}
		}
		Sheet sheet = new Sheet();
		sheet.setRoot(root);
		
		SVGCanvas canvas = new SVGCanvas();
		sheet.setTheme(new Theme()); 
		RenderBuffer.canvas = canvas;
	//	root.requestLayout();
		sheet.layoutChange(true);
		
		canvas.resize(scene.box().width(), scene.box().height());
		sheet.drawPicture(canvas);
		//RootPanel.getBodyElement().setInnerHTML(canvas.toString());
		//Window.alert((path));
		 * 	 
		 */
		ElUtils parent = ElUtils.bind(RootPanel.getBodyElement()).createDiv();
		Document.get().getElementById("gitee_icon").removeFromParent();
		
		XZMindEditor editor = new XZMindEditor(parent.toElement(),fs,file,folder);
	}
	
	

}
