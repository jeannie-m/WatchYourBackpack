<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<!-- Favicon-->
<link rel="icon" type="image/png" href="/backpack-01.png">
<!-- Bootstrap core CSS -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
<!-- Bootswatch Theme Flatly. Grab a different one from https://www.bootstrapcdn.com/bootswatch/ if you want-->
<link
	href="https://stackpath.bootstrapcdn.com/bootswatch/4.4.1/flatly/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-yrfSO0DBjS56u5M+SjWTyAHujrkiYVtRYh2dtB3yLQtUz3bodOeialO59u5lUCFF"
	crossorigin="anonymous">
<!-- Your custom styles -->
<link href="/style.css" rel="stylesheet" />
<script src="https://kit.fontawesome.com/c66cb055e4.js"
	crossorigin="anonymous"></script>
<title>Watch your Back!pack</title>
</head>
<body>
	<div class="background1">

		<div class="left-short">
			<!--  form-check disabled inputForm-left -->
			<form action="/setPlayer" method="post">
				<h2>CHOOSE YOUR PLAYER</h2>
				<h6 class="error">${ noPlayerMessage }</h6>

				<div class="form-group">


					<select name="id" class="custom-select" required
						onchange="this.form.submit()">
						<option value="0">Choose your player</option>
						<c:forEach items="${players}" var="player">
							<option value="${player.getId()}"
								<c:if test="${ gameStatus.mainPlayer.id eq player.id}">
								selected

							</c:if>>
								${player.getName()} || Attack:${player.getAttack()}:
								Fire:${player.getFire()}:
								Resourcefulness:${player.getResourcefulness()}: Money:
								<fmt:formatNumber value="${ player.money }" type="currency" />
							</option>
						</c:forEach>

					</select> <a href="/newPlayer"><h5>Add New Player</h5></a>
				</div>
			</form>
		</div>

		


		<div class="right-short" id="choose-park">
		<!-- form-check disabled inputForm-right  -->
		<h6 class="error">${ parkMessage }</h6>
			
			<form action="/start" method="post">

				<h2>CHOOSE YOUR NATIONAL PARK</h2>
				<p>(The park will only be an option if you can afford the
					entrance fee.)</p>

				<div class="btn-group-vertical">
					<!-- Browse Parks by Name -->
					<button href="#by-name" type="button" class="btn btn-primary"
						data-toggle="collapse">Browse Parks by Name</button>
					<div class="form-group collapse" id="by-name">
						<select class="custom-select" name="parkCodeName"
							onchange="this.form.submit()">
							<option value="">Browse Parks by Name</option>
							<c:forEach var="park" items="${ parksByName }">
								<option
									<c:if test="${ gameStatus.mainPlayer.money lt park.entranceFee }">
								disabled
							</c:if>
									value="${ park.getParkCode() }">${ park.getName() },
									(${ park.getStateCode() })</option>
							</c:forEach>
						</select>
					</div>
					<!-- Browse Parks by State -->
					<button href="#by-state" type="button" class="btn btn-primary"
						data-toggle="collapse">Browse Parks by State</button>
					<div class="form-group collapse" id="by-state">
						<select class="custom-select" name="parkCodeState"
							onchange="this.form.submit()">
							<option value="">Browse Parks by State</option>
							<c:forEach var="park" items="${ parksByState }">
								<option
									<c:if test="${ gameStatus.mainPlayer.money lt park.entranceFee }">
								disabled
							</c:if>
									value="${ park.getParkCode() }">${ park.getStateCode() },
									${ park.getName() }</option>
							</c:forEach>
						</select>
					</div>
					<!-- Browse Parks by Entrance Fee -->
					<button href="#by-fee" type="button" class="btn btn-primary"
						data-toggle="collapse">Browse Parks by Entrance Fee</button>
				</div>
				<div class="form-group collapse" id="by-fee">
					<select class="custom-select" name="parkCodeFee"
						onchange="this.form.submit()">
						<option value="">Browse Parks by Fee</option>
						<c:forEach var="park" items="${ parksByFee }">

							<option
								<c:if test="${ gameStatus.mainPlayer.money lt park.entranceFee }">
								disabled
						</c:if>
								value="${ park.getParkCode() }"><fmt:formatNumber
									value="${ park.getEntranceFee() }" type="currency" />, ${ park.getName() }
							</option>

						</c:forEach>
					</select>
				</div>
				</form>
		</div>		
		<!-- <button class="startButton">START GAME</button> -->
		<!-- Here is a link to STO that might help with collapsing the expanded divs when another is expanded -->
		<!-- https://stackoverflow.com/questions/37753407/bootstrap-collapse-how-to-expand-only-one-div-at-a-time -->
	</div>


	<script src="/wyb.js">
		
	</script>
	<c:if test="${ gameStatus.mainPlayer.id ne null}">
		<script>
			showDiv('choose-park');
		</script>
	</c:if>

	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
		integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
		integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
		crossorigin="anonymous"></script>
</body>
</html>