(function(angular) {
	var AppController = function($scope, $http) {
		$scope.bogus = 'Bogus';
		$scope.nominees = [];
		var session = '';

		var loadNominees = function(data) {
			console.log(JSON.stringify(data));
			session = data.session;
			$scope.nominees = data.nominees;
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

			console.log(JSON.stringify(request));

			$http(request).success(function(data) {
				console.log('Voted: ' + JSON.stringify(data));
				getNominees();
			}).error(function(e) {
				console.log('Error: ' + JSON.stringify(e));
			});
		};

		getNominees();

		var a = {
			"session" : {
				"id" : 2,
				"votes" : [ {
					"id" : 2,
					"winner" : {
						"id" : 1,
						"title" : "Le Comte de Monte-Cristo",
						"author" : "Alexandre Dumas, pére"
					},
					"loser" : {
						"id" : 2,
						"title" : "The Da Vinci Code",
						"author" : "Dan Brown"
					}
				}, {
					"id" : 3,
					"winner" : {
						"id" : 1,
						"title" : "Le Comte de Monte-Cristo",
						"author" : "Alexandre Dumas, pére"
					},
					"loser" : {
						"id" : 3,
						"title" : "Le Petit Prince",
						"author" : "Antoine de Saint-Exupéry"
					}
				}, {
					"id" : 4,
					"winner" : {
						"id" : 1,
						"title" : "Le Comte de Monte-Cristo",
						"author" : "Alexandre Dumas, pére"
					},
					"loser" : {
						"id" : 4,
						"title" : "Het Achterhuis",
						"author" : "Anne Frank"
					}
				}, {
					"id" : 5,
					"winner" : {
						"id" : 1,
						"title" : "Le Comte de Monte-Cristo",
						"author" : "Alexandre Dumas, pére"
					},
					"loser" : {
						"id" : 5,
						"title" : "The Perks of Being a Wallflower",
						"author" : "Stephen Chbosky"
					}
				}, {
					"id" : 6,
					"winner" : {
						"id" : 2,
						"title" : "The Da Vinci Code",
						"author" : "Dan Brown"
					},
					"loser" : {
						"id" : 3,
						"title" : "Le Petit Prince",
						"author" : "Antoine de Saint-Exupéry"
					}
				}, {
					"id" : 7,
					"winner" : {
						"id" : 2,
						"title" : "The Da Vinci Code",
						"author" : "Dan Brown"
					},
					"loser" : {
						"id" : 4,
						"title" : "Het Achterhuis",
						"author" : "Anne Frank"
					}
				}, {
					"id" : 8,
					"winner" : {
						"id" : 2,
						"title" : "The Da Vinci Code",
						"author" : "Dan Brown"
					},
					"loser" : {
						"id" : 5,
						"title" : "The Perks of Being a Wallflower",
						"author" : "Stephen Chbosky"
					}
				}, {
					"id" : 9,
					"winner" : {
						"id" : 3,
						"title" : "Le Petit Prince",
						"author" : "Antoine de Saint-Exupéry"
					},
					"loser" : {
						"id" : 4,
						"title" : "Het Achterhuis",
						"author" : "Anne Frank"
					}
				}, {
					"id" : 10,
					"winner" : {
						"id" : 3,
						"title" : "Le Petit Prince",
						"author" : "Antoine de Saint-Exupéry"
					},
					"loser" : {
						"id" : 5,
						"title" : "The Perks of Being a Wallflower",
						"author" : "Stephen Chbosky"
					}
				}, {
					"id" : 11,
					"winner" : {
						"id" : 4,
						"title" : "Het Achterhuis",
						"author" : "Anne Frank"
					},
					"loser" : {
						"id" : 5,
						"title" : "The Perks of Being a Wallflower",
						"author" : "Stephen Chbosky"
					}
				} ],
				"result" : null
			},
			"finished" : true,
			"nominees" : []
		};

	};

	AppController.$inject = [ '$scope', '$http' ];
	angular.module("myApp.controllers").controller("AppController",
			AppController);
}(angular));
