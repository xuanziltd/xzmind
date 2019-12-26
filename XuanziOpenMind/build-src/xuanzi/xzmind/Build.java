package xuanzi.xzmind;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * 创建 缓存菜单
 * @author 彭立铭
 *
 */
public class Build {

	public Build() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("war");
		int startPos = file.getAbsolutePath().indexOf("war")+4;
		System.out.println(file.getAbsolutePath());
		StringBuffer cache = new StringBuffer("CACHE MANIFEST\n");
		Collection<File> list = FileUtils.listFilesAndDirs(file, new IOFileFilter() {
			
			@Override
			public boolean accept(File arg0, String arg1) { 
				return true;
			}
			
			@Override
			public boolean accept(File arg0) { 
				return true;
			}
		}, new IOFileFilter() {
			
			@Override
			public boolean accept(File arg0, String arg1) { 
				return false;
			}
			
			@Override
			public boolean accept(File arg0) { 
				if(arg0.getName().equals("WEB-INF")||arg0.getName().equals("themex")||arg0.getName().equals("plugins")||arg0.getName().equals("addon"))
					return false;
				return true;
			}
		});
		long size = 0;
		for(File f:list) {
			if(f.isFile()&&(f.getName().endsWith(".html")||f.getName().endsWith(".svg")||f.getName().endsWith(".css")
					||f.getName().endsWith(".js")||f.getName().contains("webfont.ttf"))) {
				size += f.length();
				cache.append(f.getAbsolutePath().substring(startPos)).append("\n");
				System.out.println(f.getAbsolutePath().substring(startPos));
			}
		}
		cache.append("icons/fonts/fontawesome-webfont.ttf?v=4.7.0\n  \n" + 
				"NETWORK:  \n" + 
				"*\n" + 
				"\n" + 
				"# build at "+new Date().toLocaleString()+"\n" + 
				"\n" + 
				"# cache size : "+size);
		try {
			FileUtils.write(new File("war/cache.manifest"), cache);
		} catch (IOException e) { 
			e.printStackTrace();
		}
		System.out.println(cache);
	}

}
