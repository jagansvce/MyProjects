var tableApp = angular.module('tableApp', []);

tableApp.controller('TableCtrl', ["$scope", function($scope) {
	$scope.data = tableData;
	$scope.table = {};
	$scope.table.color1="#0F0",
	$scope.table.color2="#F00",
	$scope.table.columns = [
	                   {
	                	   id:"fastest",
	                	   label:"FasTest",
	                	   value1:120,
	                	   value2:20
	                   },
	                   {
	                	   id:"captureResubmit",
	                	   label:"Capture Resubmit",
	                	   value1:455,
	                	   value2:120
	                   },
	                   {
	                	   id:"emailMRDFReport",
	                	   label:"Email MRDF Report",
	                	   value1:255,
	                	   value2:20
	                   },
	                   {
	                	   id:"jobLog",
	                	   label:"Job Log",
	                	   value1:60,
	                	   value2:30
	                   }
                   ];
	
	$scope.init = function () {
		$scope.chart.$maxValue=0;
		//Identifying the max value
		$.each($scope.chart.bars, function() {
			if($scope.chart.$maxValue < (Number(this.value1) + Number(this.value2))){
				$scope.chart.$maxValue=Number(this.value1) + Number(this.value2);
			}
		});
		
		//setting width of the bar in percentage
		$.each( $scope.chart.bars, function() {
			var total = Number(this.value1) + Number(this.value2);
			this.$width = (total/$scope.chart.$maxValue) * 100;
			this.$width1 = (Number(this.value1)/total) * 100;
			this.$width2 = (Number(this.value2)/total) * 100;
			this.$successRate = this.$width1.toFixed(2);
		});

	}
	$scope.init();
} ]);