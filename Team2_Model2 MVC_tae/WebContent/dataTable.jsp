<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Data Table</title>


<!-- jQuery cdn -->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

<!-- data table cdn -->
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
<script type="text/javascript">
	//data table 생성
    /* $(document).ready(function() {
        $('#example').DataTable();
    } ); */
    $(document).ready(function(){
    	
    var table = $('#example').DataTable({
        "language": {
            "emptyTable": "데이터가 없어요.",
            "lengthMenu": "페이지당 _MENU_ 개씩 보기",
            "info": "현재 _START_ - _END_ / _TOTAL_건",
            "infoEmpty": "데이터 없음",
            "infoFiltered": "( _MAX_건의 데이터에서 필터링됨 )",
            "search": "검색: ",
            "zeroRecords": "일치하는 데이터가 없어요.",
            "loadingRecords": "로딩중...",
            "processing":     "잠시만 기다려 주세요...",
            "paginate": {
                "next": "다음",
                "previous": "이전"
            }
        },
        ajax: {
            'url':'DataTable.emp', 
            'type': 'POST',
            'dataSrc':''
         },
        columns: [
            {"data": "empno"},
            {"data": "ename"},
            {"data": "job"}, 
            {"data": "mgr"},
            {"data": "hiredate"},
            {"data": "sal"},
            {"data": "comm"},
            {"data": "deptno"},
            {"data": "img"}
        ]
    }); 
    });

</script>
</head>
<body>
	<table id="example" class="display">
		<thead>
			<tr>
				<th>사번</th>
				<th>사원 이름</th>
				<th>직급</th>
				<th>상급자</th>
				<th>고용일자</th>
				<th>급여</th>
				<th>보너스</th>
				<th>부서 번호</th>
				<th>이미지</th>
			</tr>
		</thead>
		<tbody>
			<!--      <tr>
            <td>Row 1 Data 1</td>
            <td>Row 1 Data 2</td>
        </tr>
        <tr>
            <td>Row 2 Data 1</td>
            <td>Row 2 Data 2</td>
        </tr> -->
		</tbody>
	</table>


</body>
</html>