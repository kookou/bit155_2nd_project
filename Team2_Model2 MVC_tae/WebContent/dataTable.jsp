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
	//data table ����
    /* $(document).ready(function() {
        $('#example').DataTable();
    } ); */
    $(document).ready(function(){
    	
    var table = $('#example').DataTable({
        "language": {
            "emptyTable": "�����Ͱ� �����.",
            "lengthMenu": "�������� _MENU_ ���� ����",
            "info": "���� _START_ - _END_ / _TOTAL_��",
            "infoEmpty": "������ ����",
            "infoFiltered": "( _MAX_���� �����Ϳ��� ���͸��� )",
            "search": "�˻�: ",
            "zeroRecords": "��ġ�ϴ� �����Ͱ� �����.",
            "loadingRecords": "�ε���...",
            "processing":     "��ø� ��ٷ� �ּ���...",
            "paginate": {
                "next": "����",
                "previous": "����"
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
				<th>���</th>
				<th>��� �̸�</th>
				<th>����</th>
				<th>�����</th>
				<th>�������</th>
				<th>�޿�</th>
				<th>���ʽ�</th>
				<th>�μ� ��ȣ</th>
				<th>�̹���</th>
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