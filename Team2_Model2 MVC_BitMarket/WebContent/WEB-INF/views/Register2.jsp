<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/Include/css.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
	var validate = new Array;
	$(function() {
		//id검증(이메일 형식)
		$('#id')
				.keyup(
						function() {
							let email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
							if (!email.test($(this).val())) {
								$('.tdemail')
										.html(
												'<b style="color:red">적합하지 않은 이메일 형식입니다.</b>');
								validate[0] = false;
							} else {
								$('.tdemail').html('<b>적합한 이메일입니다.</b>');
								validate[0] = true;
							}
							console.log(validate[0]);
						});

		//password
		$('#pwd')
				.keyup(
						function() {
							let pwd = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^~*+=-])(?=.*[0-9]).{8,20}$/;
							if (!pwd.test($(this).val())) {
								$('.tdpw')
										.html(
												'<b style="color:red">8~20자 사이에 적어도 하나의 영어대문자,숫자, 특수문자가 포함되어야 합니다.</b>');
								validate[1] = false;
							} else {
								$('.tdpw').html('<b>적합한 패스워드입니다.</b>');
								validate[1] = true;
							}
							console.log(validate[1]);
						});
		//password check
		$('#pwdCheck, #pwd').keyup(function() {
			if ($('#userPass').val() != $('#userPassCheck').val()) {
				$('.tdpwch').html('<b style="color:red">비밀번호가 다릅니다.</b>');
				validate[2] = false;
			} else {
				$('.tdpwch').html('<b>비밀번호가 일치합니다.</b>');
				validate[2] = true;
			}
			console.log(validate[2]);
		});

		$('input').focus(function() {
			$(this).css('background-color', "gold");
		});
		$('input').blur(function() {
			$(this).css('background-color', "white");
		});
		//입력 다 했는지 검증
		$('input:not([type=checkbox])').prop("required", true);
		// $('#userId').attr("required","required");
		//올바르지 않은 입력 검증
		$('input:submit').click(function() {
			for (let i = 0; i < validate.length; i++) {
				if (validate[i] == false) {
					alert("올바르지 않은 입력이 있습니다.");
					console.log(i);
					switch (i) {
					case 0:
						$('#userId').focus();
						return false;
					case 1:
						$('#userPass').focus();
						return false;
					case 2:
						$('#userPassCheck').focus();
						return false;
					}
				}
			}
			;

		});
	});
</script>

<style>
body {
	font-family: "malgun gothic";
	font-size: 9pt;
}

th {
	text-align: right;
	background-color: #dbdbdb
}

th.title {
	text-align: center;
	font-size: 12pt;
	background-color: #ffffff;
}
</style>


</head>


<body>
				<div class="login-form" style="text-align: center">
					<form action="BitJoinOK.bit" name="form" method="post"
						enctype="multipart/form-data">
						<div class="form-group" align="center">
							<table>
								<tr>
									<th colspan="2" class="title">회원가입</th>
								</tr>
								<tr>
									<th>아이디</th>
									<td><input type="text" maxlength="20" id="id" name="id"
										title="5~16자리의 영문+숫자 조합으로 입력해주세요"
										placeholder="이메일 형식으로 입력해 주세요"></td>
									<td class="tdemail"></td>
								</tr>
								<tr>
									<th>비밀번호</th>
									<td><input type="password" maxlength="20" id="pwd"
										name="pwd"
										title="8~20자 사이에 적어도 하나의 영어대문자,숫자, 특수문자가 포함되어야 합니다."
										placeholder="비밀번호를 입력해주세요"></td>
									<td class="tdpw"></td>
								</tr>
								<tr>
									<th>비밀번호 확인</th>
									<td><input type="password" maxlength="20" id="pwdCheck"
										name="pwdCheck" title="패스워드 확인" placeholder="비밀번호를 입력해주세요"></td>
									<td class="tdpwch"></td>
								</tr>
								<tr>
									<th>닉네임</th>
									<td><input type="text" maxlength="20" id="nick"
										name="nick" title="닉네임" placeholder="사용할 닉네임을 입력해 주세요"></td>
								</tr>
								<tr>
									<th>주소</th>
									<td><input type="text" maxlength="20" size="45" id="loc"
										name="loc" title="주소-기본주소" placeholder="동명(읍,면)으로 검색 (ex.서초동)"></td>
								</tr>
							</table>

							<div class="form-group">
								<label class="btn btn-outline-primary btn-sm">이미지 추가 
									<input type="file" name="profile" style="display: none;" onchange="readURL(this);">
								</label> 
								<span id="imgFileName">${param.profile}</span> 
								<img id="profile" src="upload/${param.profile}" alt="프로필 사진">
							</div>


							<br>
							<button type="button"
								class="btn social facebook btn-flat btn-addon mb-3">
								<i class="fa fa-crosshairs"></i>현재 위치로 찾기
							</button>
							<br> <br>
							<button type="submit"
								class="btn btn-primary btn-flat m-b-30 m-t-30">회원 가입</button>
							<button type="reset"
								class="btn btn-primary btn-flat m-b-30 m-t-30">취소</button>


						</div>
					</form>
				</div>


	<script type="text/javascript">
		function readURL(input) {
			if (input.file && input.file[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$('#profile').attr('src', e.target.result);
				}
				reader.readAsDataURL(input.files[0]);
			}
			$('#imgFileName').html(input.files[0].name);
		};
	</script>
</body>
</html>