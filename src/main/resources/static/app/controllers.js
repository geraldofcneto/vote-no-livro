(function(angular) {
	var AppController = function($scope, $http) {
		$scope.nominees = [];
		var session = '';

		var loadReport = function(){

			var loadChart = function(data){
				console.log('load chart: ' + JSON.stringify(data));

				var arr = [];
				for ( var sbook in data) {
					var book = JSON.parse(sbook);
					arr.push({
						c : [ {
							v : book.title
						}, {
							v : data[sbook]
						} ]
					})
				}
				
				$scope.chart = {};
				
			    $scope.chart.data = {"cols": [
			        {id: "t", label: "Titles", type: "string"},
			        {id: "s", label: "Ranking", type: "number"}
			    ], "rows": arr
//			    [
//			        {c: [
//			            {v: "Mushrooms"},
//			            {v: 3},
//			        ]},
//			        {c: [
//			            {v: "Pepperoni"},
//			            {v: 2},
//			        ]}
//			    ]
			    };

			    $scope.chart.type = "ColumnChart";
			    $scope.chart.options = {
			        'title': 'Livros preferidos'
			    };

			};
			
			$http({
				url : 'api/final-counting',
				method : 'GET'
			}).success(loadChart).error(function(e) {
				console.log('Error: ' + JSON.stringify(e));
			});
			
		};
				
		var loadNominees = function(data) {
			console.log(JSON.stringify(data));
			session = data.session;
			$scope.nominees = data.nominees;
			if (!data.nominees.length){
				loadReport();
			}
		};

		var getNominees = function() {
			$http({
				url : 'api/vote-no-livro',
				method : 'GET',
				params : {
					session_id : session.id
				}
			}).success(loadNominees).error(function(e) {
				console.log('Error: ' + JSON.stringify(e));
			});
		};

		$scope.vote = function(winner) {
			$scope.nominees.splice($scope.nominees.indexOf(winner), 1);

			var request = {
				url : 'api/vote-no-livro',
				method : 'POST',
				data : {
					session : session,
					winner : winner,
					loser : $scope.nominees[0]
				}
			};

			$http(request).success(function(data) {
				console.log('Voted: ' + JSON.stringify(data));
				getNominees();
			}).error(function(e) {
				console.log('Error: ' + JSON.stringify(e));
			});
		};

		getNominees();

		
	};

	AppController.$inject = [ '$scope', '$http'];
	angular.module("myApp.controllers").controller("AppController",
			AppController);
}(angular));
