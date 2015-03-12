(function(angular) {
	var AppController = function($scope, $http) {
		$scope.bogus = 'Bogus';
		$scope.nominees = [];
		var session = '';
		
		var loadNominees = function(data){
			console.log(JSON.stringify(data));
			session = data.session;
			$scope.nominees = data.nominees;
		};
		
		var getNominees = function (){
			$http({
				url : 'api/vote-no-livro',
				method : 'GET',
				params : {
					session_id : session.id 
				}
			}).success(loadNominees).error(function (e){
				console.log('Error: '+JSON.stringify(e));
			}); 
		};
		
		$scope.vote = function (winner){
			$scope.nominees.splice($scope.nominees.indexOf(winner), 1);

			var request = {
					url : 'api/vote-no-livro',
					method : 'POST',
					data : {
						session: session,
						winner: winner,
						loser: $scope.nominees[0]
					}
				};
			
			console.log(JSON.stringify(request));
			
			$http(request).success(function(data){
				console.log('Voted: ' + JSON.stringify(data));
				getNominees();
			}).error(function (e){
				console.log('Error: '+JSON.stringify(e));
			}); 
		};
		
		
		getNominees();
	};

	AppController.$inject = [ '$scope', '$http' ];
	angular.module("myApp.controllers").controller("AppController",
			AppController);
}(angular));
