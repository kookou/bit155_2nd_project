<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

	<!-- SITE TITTLE -->
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>비트 마켓</title>
	

	<!-- PLUGINS CSS STYLE -->
	<link href="plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet">
	<!-- Bootstrap -->
	<link href="plugins/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
	<!-- Font Awesome -->
	<link href="plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<!-- Owl Carousel -->
	<link href="plugins/slick-carousel/slick/slick.css" rel="stylesheet">
	<link href="plugins/slick-carousel/slick/slick-theme.css" rel="stylesheet">
	<!-- Fancy Box -->
	<link href="plugins/fancybox/jquery.fancybox.pack.css" rel="stylesheet">
	<link href="plugins/jquery-nice-select/css/nice-select.css" rel="stylesheet">
	<link href="plugins/seiyria-bootstrap-slider/dist/css/bootstrap-slider.min.css" rel="stylesheet">
	<!-- CUSTOM CSS -->
	<link href="css/style.css" rel="stylesheet">
	<link href="css/mycss.css" rel="stylesheet">
	<!-- FAVICON -->
	<link href="img/favicon.png" rel="shortcut icon">
	<!-- JAVASCRIPTS -->
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="plugins/jquery/jquery.min.js"></script>
	<script src="plugins/jquery-ui/jquery-ui.min.js"></script>
	<script src="plugins/tether/js/tether.min.js"></script>
	<script src="plugins/raty/jquery.raty-fa.js"></script>
	<script src="plugins/bootstrap/dist/js/popper.min.js"></script>
	<script src="plugins/bootstrap/dist/js/bootstrap.min.js"></script>
	<script src="plugins/seiyria-bootstrap-slider/dist/bootstrap-slider.min.js"></script>
	<script src="plugins/slick-carousel/slick/slick.min.js"></script>
	<script src="plugins/jquery-nice-select/js/jquery.nice-select.min.js"></script>
	<script src="plugins/fancybox/jquery.fancybox.pack.js"></script>
	<script src="plugins/smoothscroll/SmoothScroll.min.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCC72vZw-6tGqFyRhhg5CkF2fqfILn2Tsw"></script>
	<script src="js/scripts.js"></script>
	

  	<!-- /////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
   <div id="navbar">
		<div id="header">
			<nav class="navbar navbar-expand-lg  navigation">
				<a class="navbar-brand" href="BitImgList.bit">
					<img src="images/logo.png" alt="">
				</a>
				<div class="collapse navbar-collapse d-flex justify-content-between">
					<form action="BitImgList.bit" class="input-group" style="width: 30%;">
						<input type="text" class="form-control" placeholder="검색어를 입력해주세요" id="myinput" name="search">
						<button type="submit" id ="searchbtn" class="btn btn-outline-danger"><i class="svg-inline fa fa-search fa-w-16 fa-2x"></i></button>
					</form>
					<ul class="navbar-nav" id="navbutton">
						<li class="nav-item" style="margin-right: 15px;font-weight: 500; ">
							<a class="nav-link"href="Bitwrite.bit">글쓰기</a>
						</li>
						<li class="nav-item" style="margin-right: 15px;font-weight: 500; ">
							<a class="nav-link" href="#">마이페이지</a>
						</li>
						<li class="nav-item" style="font-weight: 500; ">
							<a class="nav-link" href="BitLogout.bit">로그아웃</a>
						</li>
					</ul>
				</div>
			</nav>
		</div>
	</div>
</head>