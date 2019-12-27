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

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import sbaike.client.h5.client.Action;
import sbaike.client.h5.client.ElUtils;
import sbaike.client.h5.client.LocalStorage;
import xuanzi.h5.fs.client.IndexedFSView;
import xuanzi.h5.fs.client.PopupMenu;
import xuanzi.h5.fs.core.IFile;
import xuanzi.h5.fs.core.Result;
import xuanzi.h5.fs.impl.BFile;
import xuanzi.h5.fs.impl.FileSystem;
import xuanzi.xzmind.core.IXZFileManager;

/**
 * 扩展 IndexedFSView 组件，增加思维导图管理功能
 * 
 * @author 彭立铭
 *
 */
public class XZFileManager extends IndexedFSView implements IXZFileManager{

	
	
	private Action filePopupAction;

	/**
	 * 渲染文件列表，增加地址栏的路径表示
	 */
	@Override
	protected void onRenderList(List<BFile> items, ElUtils listEl2) {
	 
		String url = Window.Location.getHref();
		int p = url.indexOf("#");
		if(p==-1)
			url +="#indexed://"+getFs().getCurrentPath();
		else {
			url = url.substring(0,p)+"#indexed://"+getFs().getCurrentPath();
		}
		Window.Location.assign(url);
		super.onRenderList(items, listEl2);
	}
	
	public XZFileManager(Element parent,FileSystem fs, String folder) {
		super(parent,fs,folder);
		parent.addClassName("fs-filemanger");
	}
	
	NavPanel navPanel ;
	
	
	
	@Override
	protected void onCreateViews(ElUtils viewBodyEl) {
		navPanel = new NavPanel(this);
		navPanel.onLoad(getBodyEl());
		 
		super.onCreateViews(viewBodyEl);
	}
	
	@Override
	public void onLoad() {
		Window.addResizeHandler(new ResizeHandler() {
			
			@Override
			public void onResize(ResizeEvent event) {
				getBodyEl().style().setHeight(event.getHeight()	, Unit.PX);
			}
		});
		getBodyEl().style().setHeight( Window.getClientHeight()	, Unit.PX);
		
		super.onLoad();
	}

	@Override
	protected void onCreatePopupMenu(PopupMenu pm, Event event) {
		// TODO Auto-generated method stub
		super.onCreatePopupMenu(pm, event);
	}
	
	@Override
	protected void onFileItemClick(BFile file, Event event) {
		// TODO Auto-generated method stub
		super.onFileItemClick(file, event);
	}
	
	@Override
	protected String safeFileName(String name) {
		return name.toLowerCase().endsWith(".md")?super.safeFileName(name):(super.safeFileName(name)+".md");
	}
	/**
	 * 初始化顶部工具栏
	 */
	@Override
	protected void onInitToolbar(ElUtils toolbar) {
		 Action applyLightAction = new Action() { 
			@Override
			public void execute(Element el, Event event) { 
				LocalStorage.set("fs-theme", null);
				RootPanel.getBodyElement().removeClassName("fs-theme-night"); 
			}
		};
		Action applyNightAction = new Action() { 
			@Override
			public void execute(Element el, Event event) {
				LocalStorage.set("fs-theme", "fs-theme-night");
				RootPanel.getBodyElement().addClassName("fs-theme-night");
			}
		};
		Action questionAction = new Action() {
			
			@Override
			public void execute(Element el, Event event) { 
				Window.open("help.html", "_blank", null);
			}
		};
		toolbar.createEl("img").attr("src", "logo.png").addClass("fs-logo");
		toolbar.createSpan("玄子思维导图 v0.2").addClass("fa  fs-apps");
		toolbar.createButton("").attr("title", "帮助文档").click(questionAction  ).addClass("fs-fr fa fa-question");
		toolbar.createButton("").click(applyLightAction).addClass("fa fa-circle fs-fr").attr("style", "color:#eee;background:none;margin:0px");
		toolbar.createButton("").click(applyNightAction).addClass("fa fa-circle fs-fr").attr("style", "color:#555;background:none;margin:0px");
		super.onInitToolbar(toolbar);
	}
	
	/**
	 * 重写建立文件的方法
	 */
	@Override
	protected void onCreateFile() {
		String name = Window.prompt("输入文件名称", "新建脑图文档");
		if(name!=null&&name.length()>0) {
		 
			getFs().createFile(getFs().getCurrentFolder(), safeFileName(name), new Result() { 
				@Override
				public void result(IFile parent) {
					renderList();
				}
			});
		}
	}
	
	@Override
	protected void onFileIconCreate(ElUtils itemEl, BFile item) {
		itemEl.createEl("div").addClass("fa fs-file-icon fa-"+(item.isFile()?"file-text-o":"folder"));
	}
	
	@Override
	protected void onFileClick(BFile file) { 
		open(file.getName());
		
	}
	
	@Override
	protected void onFilePopupMenu(PopupMenu pm, BFile item, Event event) { 
		super.onFilePopupMenu(pm, item, event);
	}

	/**
	 * 打开当前目录的某个文件
	 * 
	 * @param name
	 */
	public void open(String name) {
		String url = Window.Location.getHref();
		int p = url.indexOf("#");
		if(p==-1)
			url +="#indexed://"+getFs().getCurrentPath();
		else {
			url = url.substring(0,p)+"#indexed://"+getFs().getCurrentPath()+"/"+name;
		}
		Window.open(url, "_blank", null);
	}
	
	/**
	 * 初始化第一次打开时候的数据
	 */ 
	protected void onInitData(final Result result) {
		if(defaultFolder.equals("/项目库")) {  
			getFs().goFolder(defaultFolder, new Result() {
				
				@Override
				public void result(final IFile folder) { 
					if(folder.getFolderCount()==0&&folder.getFileCount()==0)
						getFs().mkdirs(folder, new String[] {"工作笔记","学习资料","读书笔记","头脑风暴"}, new Result() {
							
							@Override
							public void result(IFile parent) { 
								result.result(parent);
							}
						});
					else {
						result.result(folder);
					}
				}
			}); 
		} 
	}
}
