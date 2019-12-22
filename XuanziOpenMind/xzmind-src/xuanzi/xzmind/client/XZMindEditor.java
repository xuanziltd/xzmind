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
 
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import sbaike.client.h5.client.Action;
import sbaike.client.h5.client.ElUtils;
import sbaike.client.h5.client.JQuery;
import xuanzi.commons.graphics.Paint;
import xuanzi.commons.graphics.SVGCanvas;
import xuanzi.h5.fs.client.PopupMenu;
import xuanzi.h5.fs.core.FileInfo;
import xuanzi.h5.fs.core.FileReader;
import xuanzi.h5.fs.core.FileWriter;
import xuanzi.h5.fs.core.IFile;
import xuanzi.h5.fs.core.Log;
import xuanzi.h5.fs.impl.BFileBytes;
import xuanzi.h5.fs.impl.FileSystem;
import xuanzi.openmind.layout.Box;
import xuanzi.openmind.lines.Line;
import xuanzi.openmind.lines.RoundLine;
import xuanzi.openmind.nodes.Node;
import xuanzi.openmind.nodes.Sheet;
import xuanzi.openmind.renders.RenderBuffer;
import xuanzi.openmind.scenes.DefaultScene;
import xuanzi.openmind.scenes.base.EastScene;
import xuanzi.openmind.scenes.base.NorthScene;
import xuanzi.openmind.scenes.base.Scene;
import xuanzi.openmind.scenes.base.SouthScene;
import xuanzi.openmind.scenes.base.ThinkTreeNorthScene;
import xuanzi.openmind.scenes.base.WestScene;
import xuanzi.openmind.shapes.Round;
import xuanzi.openmind.themes.Theme;
 
/**
 * 文档编辑器
 * 
 * @author 彭立铭
 *
 */
public class XZMindEditor extends Module{
	
	String id = "xx";
	
	FileSystem fs ;
	
	ElUtils parent ;
	
	String path;

	private IFile currentFile;
	
	public IFile getCurrentFile() {
		return currentFile;
	}
	
	public FileSystem getFs() {
		return fs;
	}
	
	public String getPath() {
		return path;
	}
	 
	PrintService printService = new PrintService(this);
	
	/**
	 * 文件模块就绪，渲染文档
	 * 
	 * @param bytes	文档正文
	 */
	private void onReadyFile(String bytes) {
		ElUtils renderEl = parent.createDiv().attr("id",id);
		render(parent.toElement(), id, Window.getClientHeight()-2, false,bytes);
	}
	
	private Action saveAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) {
			
		 
					fs.writeFile(currentFile, new FileWriter() {
						@Override
						public void finish(BFileBytes fbb) {
							
						}
						
						@Override
						public String toString() { 
							return getMdText();
						}
					});
					Toast.text("已保存").show();
			 
		}
	};
	


	private Action createMindAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) { 
			PopupMenu pm = new SamplePopupMenu(XZMindEditor.this,event); 
			pm.show();
		}
	};

	ExportDocumentService exportService = new ExportDocumentService(this);
	

	public XZMindEditor(Element element,FileSystem fs2, IFile file,String filePath) {
		this.fs = fs2; 
		this.path = filePath;
		currentFile = file;
		parent = ElUtils.bind(element); 
		ElUtils toolbar = parent.createDiv().addClass("fs-toolbar");
		toolbar.createEl("img").attr("src", "logo.png").addClass("fs-logo");
		toolbar.createSpan("玄子思维导图").addClass("fa  fs-apps");
		toolbar.createSpan(path.replace("/", " / ")).addClass("fa fa-folder fs-apps");
		toolbar.createButton("思维导图").addClass("fa fa-plus").click(createMindAction );
		toolbar.createButton(" 保  存").click(saveAction).addClass("fs-fr fa fa-save");
		toolbar.createButton("").attr("title", "下载文件").click(exportService.downloadAction ).addClass("fs-fr fa fa-download");
		toolbar.createButton("").attr("title", "打印文档").click(printService.printAction ).addClass("fs-fr fa fa-print");
		Window.setTitle(file.getName()+"-玄子思维导图");
		ce = DOM.createElement("canvas").cast();
		fs.readFile(file, new FileReader() {
			
			@Override
			public void result(BFileBytes fbb) {
				onReadyFile(fbb.getBytes());
				startSaveTask();
			} 
		});
		
		
 
	}
 
	/**
	 * 开始自动保存任务
	 */
	protected void startSaveTask() {
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				fs.writeFile(currentFile, new FileWriter() {
					@Override
					public void finish(BFileBytes fbb) {
						
					}
					
					@Override
					public String toString() { 
						return getMdText();
					}
				});
				Log.log("save ..");
			}
		};
		timer.scheduleRepeating(8*1000);
	}

	CanvasElement ce ;

	/**
	 * 解析思维导图的接口方法
	 * @param text	MD原文
	 * @return 成功渲染的思维导图SVG
	 */
	public String supermind(String text) {
		Node root = parseText(text); 
		root.setShape(new Round());
		root.setLine(new RoundLine());
		Sheet sheet = new Sheet();
		sheet.setRoot(root);
		
		SVGCanvas canvas = new SVGCanvas() {
			@Override
			public float textWidth(String text, boolean mline, Paint paint) {
				if(text==null)
					return 0; 
				ce.getContext2d().setFont(paint.textSize+"px 微软雅黑");
				return (float) ce.getContext2d().measureText(text).getWidth();
			}
		};
		sheet.setTheme(new Theme()); 
		RenderBuffer.canvas = canvas; 
		sheet.layoutChange(false); 
		Box bound = root.getScene().box();
		bound.inset(-10, -10);
		canvas.resize(bound.width(),bound.height());
		sheet.drawPicture(canvas);
		return canvas.toString();
	}
	
 
	/**
	 * 解析MD原文格式
	 * @param text2
	 * @return
	 */
	public static Node parseText(String text2) {
		 
		pos = 1;
		
		Node root = new Node();
		root.setLine(new Line());
		
		String list[]=text2.split("\n");
		String first = list[0];
		if(first.startsWith(":")) {
			String key = first.substring(1); 
			Scene scene = new EastScene();
			if("逻辑图-向左".equals(key))
				scene = new WestScene();
			else if("思维树图".equals(key))
				scene = new ThinkTreeNorthScene();
			else if("组织架构图".equals(key))
				scene = new SouthScene();
			else if("组织架构图-向上".equals(key))
				scene = new NorthScene();
			root.setScene(scene);
			first = list[1];
			pos = 2;
		}
		first= first.replace("\\n", "\n");
		if(first.startsWith("-")) {
			//root.setOpen(false);
			first.substring(1);
		}
			
		root.setTopic(first);
		if(first.contains(":")) {
			int p = first.indexOf(":");
			root.setLabel(first.substring(0,p));
			root.setTopic(first.substring(p+1));
		}
		listX(root, list , -1 );
		return root;
	}
	
	public static int level(String s) {
		if(s==null)
			return 0;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)!=' ') {
				return i;
			}
		}
		return s.length();
	}

	
	static int pos = 0;
	/**
	 * 
	 * @param node
	 * @param list
	 * @param plevel
	 */
	private static void listX(Node node,String[] list,int plevel ) {
		Node lastNode = node;
		
		while(pos<list.length){
			String topic = list[pos];
			if(topic==null||topic.trim().length()==0) {
				
			}else {
				int l = level(topic); 
				topic = topic.trim().replace("\\n", "\n");
				boolean open = true;
				if(topic.startsWith("---")) {
					open = false;
					topic = topic.substring(3);
				}
				Node child = new  Node(topic);  
				if(topic.contains(":")) {
					int p = topic.indexOf(":");
					child.setLabel(topic.substring(0,p));
					child.setTopic(topic.substring(p+1));
				}
				if(l>plevel) {  
					lastNode.addNode(child); 
					node = (Node) lastNode;
				}else if(l<plevel){
					int x = (plevel-l)/4;
					for(int i=0;i<x;i++) {
					 if(node.getParent()!=null)
					 	node = (Node) node.getParent();
					} 
					node.addNode(child);
				}else{  
					node.addNode(child); 
				}
				child.setOpen(open);
				lastNode = child;
				plevel = l;
			}
			pos++;
			
		}
	}

	private native void render(Element el, String id, int xheight, boolean md,String text) /*-{ 
		var $this = this;
		var phone = false;
		var config = {
			supermind : {
				render : function(text) { 
					return $this.@xuanzi.xzmind.client.XZMindEditor::supermind(Ljava/lang/String;)(text);
				}
			},
			width : "99.8%",
			height : xheight - 60,
			emoji : true,
			taskList : true,
			markdown : text ,
			tex : true,
			toc : true,
			codeFold : true,
			tocm : true,
			tocDropdown : true,
			flowChart : true,
			sequenceDiagram : true,
			syncScrolling : true,
			path : "editor.md/lib/"
		};
	 
		try {
			if (md) {
				$wnd.editormd.markdownToHTML(id, config);
			} else
				this.editor = $wnd.editormd(id, config);
		} catch (e) {
			console.dir(e);
		}
		this.editorId = id;
		this.editorConfig = config;
	}-*/;

	public native void setMdText(String text) /*-{
		this.editor.setMarkdown(text);
	}-*/;
	
	public native void setRenderText(String text) /*-{
		this.editorConfig.markdown = text;
		$wnd.editormd.markdownToHTML(this.editorId, this.editorConfig);
	}-*/;

	public native String getMdText()/*-{
		return this.editor.getMarkdown();
	}-*/;

	public void insertText(String text) { 
			insertMdText(text);
	}

	public native void insertMdText(String text) /*-{
		this.editor.insertValue(text);
	}-*/;

	/**
	 * 得到渲染后的文本
	 * @return
	 */
	public String getRenderedText() {
		StringBuffer sb = new StringBuffer("<html><head><link rel=\"stylesheet\" href=\"https://xzmind.xuanzi.ltd/editor.md/css/editormd.preview.min.css\"><link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/KaTeX/0.3.0/katex.min.css\"></head>\n<body>");
		sb.append("<div class=\"editormd-preview-container\"><div class=\"markdown-body\">\n");
		sb.append(JQuery.$(".editormd-preview-container").html());
		sb.append("\n</div></body>\n</html>");
		return sb.toString();
	}

}
