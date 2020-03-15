package com.shenl.project.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class CosUtils {

	public static final String SecretId = "AKIDQm15ygoqAMSVOB9TqDTq8VmsushRoOdp";
	public static final String Secretkey = "DRXSDnIi5IKArbGk5FD2gAUedRo13tY1";
	public static final String bucketName = "sta-1254206103";

	public static COSClient initCos() {
		// 1 初始化用户身份信息(secretId, secretKey)
		COSCredentials cred = new BasicCOSCredentials(SecretId, Secretkey);
		// 2 设置bucket的区域, COS地域的简称请参照
		// https://cloud.tencent.com/document/product/436/6224
		ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));
		// 3 生成cos客户端
		COSClient cosclient = new COSClient(cred, clientConfig);
		// bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式


		// 权限信息中身份信息有格式要求, 对于根账户与子账户的范式如下：
		// 下面的 root_uin 和 sub_uin 都必须是有效的 QQ 号
		// 根账户 qcs::cam::uin/<root_uin>:uin/<root_uin> 表示授予根账户 root_uin 这个用户(即前后填的 uin 一样)
		//  如 qcs::cam::uin/2779643970:uin/2779643970
		// 子账户 qcs::cam::uin/<root_uin>:uin/<sub_uin> 表示授予 root_uin 的子账户 sub_uin 这个客户
		//  如 qcs::cam::uin/2779643970:uin/73001122

		/*AccessControlList acl = new AccessControlList();
		Owner owner = new Owner();
		// 设置 owner 的信息, owner 只能是根账户
		owner.setId("qcs::cam::uin/1254206103:uin/100004557846");
		acl.setOwner(owner);

		// 授权给根账户 73410000 可读可写权限
		UinGrantee uinGrantee1 = new UinGrantee("qcs::cam::uin/1254206103:uin/100004557846");
		acl.grantPermission(uinGrantee1, Permission.FullControl);*/
		// 授权给 2779643970 的子账户 72300000 可读权限
	/*	UinGrantee uinGrantee2 = new UinGrantee("qcs::cam::uin/2779643970:uin/72300000");
		acl.grantPermission(uinGrantee2, Permission.Read);
		// 授权给 2779643970 的子账户 7234444 可写权限
		UinGrantee uinGrantee3 = new UinGrantee("qcs::cam::uin/7234444:uin/7234444");
		acl.grantPermission(uinGrantee3, Permission.Write);*/

       /* cosclient.setObjectAcl(bucketName,"",acl);*/


		return cosclient;

	}

	public static String GeneratePresignedUrl(String keyset) {
		// 生成一个下载链接
		// bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
		String key = keyset;
		GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
		// 设置签名过期时间(可选), 若未进行设置, 则默认使用ClientConfig中的签名过期时间(5分钟)
		// 这里设置签名在半个小时后过期
		Date expirationDate = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
		req.setExpiration(expirationDate);
		URL downloadUrl = initCos().generatePresignedUrl(req);
		String downloadUrlStr = downloadUrl.toString();
		return downloadUrlStr;
	}

	public static String upLoadCos(InputStream inputStream, String key, Long size){
		// 根据用户名字 上传图片  流的形式
		String etag="";
		/*InputStream inputStream =new ByteArrayInputStream(imageInputStream.getBytes());*/
		ObjectMetadata objectMetadata = new ObjectMetadata();
		// 设置输入流长度为 500
		/*int i=imageInputStream.getBytes().length;*/
		System.out.println(size+"");
		objectMetadata.setContentLength(size);
		// 设置 Content type, 默认是 application/octet-stream
		objectMetadata.setContentType("video/mpeg4");
		try {
			PutObjectResult putObjectResult = initCos().putObject(CosUtils.bucketName, key, inputStream, objectMetadata);
			System.out.println(etag+"/"+putObjectResult.getContentMd5());
			etag = "success";
		} catch (CosClientException  e) {
			// TODO: handle exception
			etag="failure";
		}
		/*utObjectResult putObject(String bucketName, String key, InputStream input,
		        ObjectMetadata metadata) throws CosClientException, CosServiceException;*/

		// 根据结果 添加数据库，如果成功 对数据库进行维护
		return etag;
	}
}
