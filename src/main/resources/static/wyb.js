/**
 * 
 */
	function showDiv(divId){
		document.getElementById(divId).style.display="block";
	}
	function wonGame(){
		alert("You have successfully made it out alive!");
	}
	
		function toggleDropdownByName() {
			document.getElementsByClassName("browse-by-name").classList
					.toggle("show");
		}
		function toggleDropdownByState() {
			document.getElementsByClassName("browse-by-state").classList
					.toggle("show");
		}
		function toggleParkDisplay(change) {
			let state = change.data.state;
			document.getElementsByClassName("parks-in-state").classList
					.toggle("show");
		}