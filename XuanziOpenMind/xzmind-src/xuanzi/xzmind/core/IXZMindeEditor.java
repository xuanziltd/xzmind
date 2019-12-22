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

package xuanzi.xzmind.core;

import xuanzi.h5.fs.core.IFile;
import xuanzi.h5.fs.impl.FileSystem;

public interface IXZMindeEditor {

	FileSystem getFs();

	String getRenderedText();

	void insertMdText(String text);

	String getMdText();

	void setRenderText(String text);

	void insertText(String text);

	void setMdText(String text);

	IFile getCurrentFile();

	String getPath();

	void height(int height);

	
}
