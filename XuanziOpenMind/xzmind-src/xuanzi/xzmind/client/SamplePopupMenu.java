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

import sbaike.client.h5.client.Action;
import sbaike.client.h5.client.JQuery;
import xuanzi.h5.fs.client.PopupMenu;

/**
 * 例子菜单
 * @author 彭立铭
 *
 */
public class SamplePopupMenu extends PopupMenu{

	XZMindEditor editor;
	
	public SamplePopupMenu(XZMindEditor editor_, Event event) {
		super(event);
		this.editor = editor_;
		addMenuItem("逻辑图", new Action() {
			
			@Override
			public void execute(Element el, Event event) {
				// TODO Auto-generated method stub
				editor.insertMdText("###思维导图样例\r\n"
						+ "```mind\r\n" + 
						":逻辑图\r\n" + 
						"样例 : 插入思维导图\r\n" + 
						"	第一项 : 样例节点\r\n" + 
						"		子节点\r\n" + 
						"		子节点\r\n" + 
						"	第二项 : 样例节点\r\n" + 
						"	第三项 : 样例节点\r\n" + 
						"```\r\n" + 
						"> 说明：用TAB键划分节点层次\r\n\r\n" );
			}
		});
		
		addMenuItem("逻辑图-向左", new Action() {
			
			@Override
			public void execute(Element el, Event event) {
				// TODO Auto-generated method stub
				editor.insertMdText("###思维导图样例\r\n"
						+ "```mind\r\n" + 
						":逻辑图-向左\r\n" + 
						"样例 : 插入思维导图\r\n" + 
						"	第一项 : 样例节点\r\n" + 
						"		子节点\r\n" + 
						"		子节点\r\n" + 
						"	第二项 : 样例节点\r\n" + 
						"	第三项 : 样例节点\r\n" + 
						"```\r\n" + 
						"> 说明：用TAB键划分节点层次\r\n\r\n" );
			}
		});
	 
		/*
		addMenuItem("组织架构图", new Action() {
			
			@Override
			public void execute(Element el, Event event) {
				// TODO Auto-generated method stub
				editor.insertMdText("###思维导图样例\r\n"
						+ "```mind\r\n" + 
						":组织架构图\r\n" + 
						"样例 : 插入思维导图\r\n" + 
						"	第一项 : 样例节点\r\n" + 
						"		子节点\r\n" + 
						"		子节点\r\n" + 
						"	第二项 : 样例节点\r\n" + 
						"	第三项 : 样例节点\r\n" + 
						"```\r\n" + 
						"> 说明：用TAB键划分节点层次\r\n\r\n" );
			}
		});
		
		addMenuItem("组织架构图-向上", new Action() {
			
			@Override
			public void execute(Element el, Event event) {
				// TODO Auto-generated method stub
				editor.insertMdText("###思维导图样例\r\n"
						+ "```mind\r\n" + 
						":组织架构图-向上\r\n" + 
						"样例 : 插入思维导图\r\n" + 
						"	第一项 : 样例节点\r\n" + 
						"		子节点\r\n" + 
						"		子节点\r\n" + 
						"	第二项 : 样例节点\r\n" + 
						"	第三项 : 样例节点\r\n" + 
						"```\r\n" + 
						"> 说明：用TAB键划分节点层次\r\n\r\n" );
			}
		});
		*/
	}
 
	

}
