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
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

import sbaike.client.h5.client.Action;
import sbaike.client.h5.client.ElUtils;
import xuanzi.h5.fs.core.FileWriter;
import xuanzi.h5.fs.core.IFile;
import xuanzi.h5.fs.core.IFileSystem;
import xuanzi.h5.fs.core.Result;
import xuanzi.h5.fs.impl.BFileBytes;
import xuanzi.h5.fs.impl.FileSystem;

/**
 * 侧边栏
 * 
 * @author 彭立铭
 *
 */
public class NavPanel extends Module {

	public NavPanel() {
		
	}
	
	XZFileManager manager;
	
	public NavPanel(XZFileManager xzFileManager) {
		this.manager = xzFileManager;
	}

	ElUtils panelEl;
	
	ElUtils listEl;
	
	/**
	 * 插入样例文件
	 * @param name
	 * @param result
	 */
	private void insertFile(final String name,final String result) {
		final IFileSystem fs = manager.getFs(); 
		manager.getFs().createFile(fs.getCurrentFolder(), name , new Result() {
			
			@Override
			public void result(IFile parent) { 
				fs.writeFile(parent, new FileWriter() {
					
					@Override
					public void finish(BFileBytes fbb) {
						manager.renderList();
						Toast.text("创建完毕").show();
						Timer timer = new Timer() {
							
							@Override
							public void run() {
								if(Window.confirm("创建完毕，是否现在打开？")) {
									manager.open(name);
								}
							}
						};
						timer.schedule(300);
					}
					@Override
					public String toString() {
						return result;
					}
				});
			}
		});
	}

	private Action createSampleAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) {
			Assets.get("samples/sample.md", new Assets.Result() {
				
				@Override
				public void back(String result) {
					insertFile("样例文档.md",result);
				} 
			}); 
		}
	};
	
	

	private Action createSimpleSampleAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) { 
			Assets.get("samples/simpleSample.md", new Assets.Result() {
				
				@Override
				public void back(String result) {
					insertFile("简单样例.md",result);
				} 
			}); 
		}
	};
	
	private Action createFirstSampleAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) {
			Assets.get("samples/firstSample.md", new Assets.Result() {
				
				@Override
				public void back(String result) {
					insertFile("入门样例.md",result);
				} 
			}); 
		}
	};

	private Action goHomeAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) {
			manager.goFolder("/项目库", new Result() {
				
				@Override
				public void result(IFile parent) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	};

	private Action opensourceAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) {
			Window.open("https://gitee.com/xuanzi_code", "_blank", null);
		}
	};

	private Action joinQQGroupAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) {
			Window.open("//shang.qq.com/wpa/qunwpa?idkey=be9c35b48cb24be8f5e69bc416adf66e0af124ada43456845b3151934e1be6e3", "_blank", null);
			
		}
	};

	private Action goHomepageAction = new Action() {
		
		@Override
		public void execute(Element el, Event event) {
			Window.open("https://xuanzi.ltd", "_blank", null);
		}
	};



	public void onLoad(ElUtils bodyEl) { 
		panelEl = bodyEl.createDiv("").addClass("nav-panel");
		listEl = panelEl.createEl("ul").addClass("fs-nav-list");
		listEl.createEl("li").createButton("本机文件").click(goHomeAction ).addClass("fa fa-folder");
		listEl.createEl("hr");
		listEl.createEl("li").createButton("简单样例").click(createSimpleSampleAction ).addClass("fa fa-file"); 
		listEl.createEl("li").createButton("入门样例").click(createFirstSampleAction ).addClass("fa fa-file");
		listEl.createEl("li").createButton("完整样例").click(createSampleAction  ).addClass("fa fa-file");
		  
		ElUtils infoEl = panelEl.createDiv().addClass("fs-site-info fs-nav-list");
		infoEl.createEl("li").createButton("开源社区").click(opensourceAction   ).addClass("fa fa-github");
		infoEl.createEl("li").createButton("开发者交流群").attr("title", "345694159").click(joinQQGroupAction  ).addClass("fa fa-qq"); 
		
		infoEl.createEl("li").createButton("开发者网站").click(goHomepageAction   ).addClass("fa fa-home");
	}

}
