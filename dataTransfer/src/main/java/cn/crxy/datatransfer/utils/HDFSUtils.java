package cn.crxy.datatransfer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HDFSUtils {
	
	public static Boolean isExisted(String hdfsPath) throws Exception {
		Configuration conf = new Configuration();
		//以下两行配置是为了解决错误No FileSystem for scheme: hdfs
		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
		FileSystem fileSystem = FileSystem.get(new URI("hdfs://crxy99:9000"), conf);
		return fileSystem.exists(new Path(hdfsPath));
	}

	public static void local2Hdfs(String localPath,String hdfsPath) throws Exception {
		Configuration conf = new Configuration(); 	
		FileSystem fileSystem = FileSystem.get(new URI("hdfs://crxy99:9000"), conf);
		FSDataOutputStream out = fileSystem.create(new Path(hdfsPath));
		InputStream in = new FileInputStream(new File(localPath));
		IOUtils.copyBytes(in, out, 1024*1024, true);
	}

}
