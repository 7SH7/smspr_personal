package org.example.smspr.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

// 여기의 방법은 S3에 올리는 게 아니라 로컬에 저장하는 방법..
@Component
public class FileUpload {

	private final KeysProperties keysProperties;
	public FileUpload(KeysProperties keysProperties) {
		this.keysProperties = keysProperties;
	}

	// MultipartFile files : 업로드된 파일
	// HttpServletRequest request : 파일을 저장할 경로를 얻기 위해 사용
	public String s3(MultipartFile files, HttpServletRequest request){
		// return 해줄 문자 초기화
		String returnValue = "";

		try{
			// amazon 쓰니까, 자격증명(credentials) 해줘야지..
			AWSCredentials credentials = new BasicAWSCredentials(keysProperties.getAccess_key(), keysProperties.getSecret_key());

			// 자격 증명 했으니, 이제 필요한 정보 가져와야지
			AmazonS3 s3 =
				// AmazonS3ClientBuilder.standard() : 클라이언트 객체를 생성하기 위한 빌더 클래스.
				// standard() 메서드는 기본 설정으로 클라이언트를 생성하는 빌더를 반환
				AmazonS3ClientBuilder
					.standard()
					// AWS에 접근하기 위해 필요한 인증 정보를 설정
					// AWSStaticCredentialsProvider는 정적(하드 코딩)인 자격 증명을 제공하는 클래스
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(Regions.AP_NORTHEAST_2)
					.build();

			// ObjectMetadata : S3에 저장될 metadata를 의미하는 class
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(files.getSize());
			metadata.setContentType(files.getContentType());

			// S3에 저장될 때의 unique한 value 설정
			String fileName = setFileName(files);

			// PutObjectRequest는 S3에 객체를 업로드하기 위한 요청을 encapsulate하는 클래스
			// new PutObjectRequest(bucketName(저장될 버킷 이름), key(객체 저장을 위한 고유 식별자)
			// , input stream(upload하려는 파일의 input sream), metadata)
			PutObjectRequest putObjectRequest = new PutObjectRequest(
				keysProperties.getBucket_name() + "/" + keysProperties.getProject_folder_name()
				, fileName
				, files.getInputStream()
				, metadata
			);

			// ACL : Access Control List : 객체 접근 권한 관리 목록
			// Canned ACL: 미리 정의된 권한 세트를 사용하여 ACL을 설정하는 방법
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);    // PublicRead: 모든 사용자가 객체를 읽을 수 있음.
			s3.putObject(putObjectRequest);

			// 업로드된 파일에 접근하기 위한 값 (s3_url(https:// + bucket 이름 + .s3.amazonaws.com/ 을 포함하고 있음)  + floder명 + file명
			returnValue = keysProperties.getRead_s3_url() + keysProperties.getProject_folder_name() + "/" + fileName;

		} catch (IOException e) {
			System.out.println("exception 종류 : " + e);
		}
		return returnValue;
	}

	// time(now) + filename 해주는 것
	private static String setFileName(MultipartFile files) {
		String result = "";

		// files는 단일 string이 아니라서 이렇게 처리 하는 것.
		if(files == null || "".equals(files.getOriginalFilename() + "")){
		} else {
			Date date = new Date();
			// ""는 String화 하기 위해 하는 것
			String temp_date = date.getTime() + "";

			String fileName = files.getOriginalFilename();

			// ~~.jpg에서 확장자(jpg)만 빼내는 것! (사용은 안 하지만, 이런 것도 된다~)
			String extension  = FilenameUtils.getExtension(fileName);
			if(extension == null || "".equals(extension)) {
				extension = "temp_amuna";
			}

			fileName = fileName.replace(" ", "");
			result = temp_date + "_" + fileName;
			// 올리는시간(milsec)_파일이름 으로 저장됨
			// ex) 1672531199000_sky.jpg 이런 식으로..
		}
		return result;
	}

	public static String local(MultipartFile files, HttpServletRequest request) {
		// local은 주소 직접 지정이 필요함.
		String returnValue = "";
		try{
			String root_path = path(request);   // 현재 파일의 위치를 가져와서, 파일 위치가 C:/ 에 있는 것이 맞는지 확인 후, 저장하고자 하는 위치로 일부 주소 변경
			setDir(root_path);   // 저장하고자 하는 곳의 풀더의 유무 확인.
			String fileName = setFileName(files); // 파일 이름 수정

			// File 도구(Util)를 통해 copy(저장하는 거임!)
			FileCopyUtils.copy(files.getBytes(), new File(root_path + fileName));
			
			// 주소 지정
			returnValue = "/uploadfile/" + fileName;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return returnValue;
	}

	private static void setDir(String rootPath) {
		// directory 주소에 대해서, file 객체에 담음. (풀더를..)
		File newfile = new File(rootPath);

		if(!newfile.exists()) newfile.mkdir();
	}

	public static String
	path(HttpServletRequest request) throws IOException{
		String root_path = rootPath(request);

		// 서버 upload 이후를 위한 코드
		root_path = root_path.replace("wtpwebapps", "uploadfiles");   // wtpwebapps는 Eclipse와 같은 IDE에서 개발할 때 사용되는 임시 경로
		root_path = root_path.replace("webapps", "uploadfiles");   // Tomcat의 경우 애플리케이션이 webapps 폴더 아래에 위치.

		// local server를 위한..
		if(root_path.indexOf("C:/") == 0){  // window || C:/는 제일 앞에 붙으니, 0번째에 위치해 있으면..
			root_path = "C:/workspace/";
		} else if(root_path.indexOf("Users/") == 0){  // mac  || 마찬가지로 처음에 위치해 있으면..
			root_path = "Users/workspace/";
		}

		String attachedPath = "uploadfiles/smspr/";
		return root_path + attachedPath;
	}

	private static String rootPath(HttpServletRequest request) {
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		rootPath = rootPath.replace("\\","/");
		return rootPath;
	}


}
