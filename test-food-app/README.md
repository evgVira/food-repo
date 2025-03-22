для запуска приложения необходимо:
1) поднять контейнер с базой данных
- username: root 
- password: root
- url: jdbc:postgresql://localhost:5434/food-db

2) запустить приложение, по умолчанию на порту 8082
3) swagger доступен по ссылке http://localhost:8082/swagger-ui/index.html
4) создать пользователя http://localhost:8082/swagger-ui/index.html#/%D0%9A%D0%BE%D0%BD%D1%82%D1%80%D0%BE%D0%BB%D0%BB%D0%B5%D1%80%20%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D0%B5%D0%BB%D1%8F/createUser
5) создать новое блюдо http://localhost:8082/swagger-ui/index.html#/%D0%9A%D0%BE%D0%BD%D1%82%D1%80%D0%BE%D0%BB%D0%BB%D0%B5%D1%80%20%D0%B1%D0%BB%D1%8E%D0%B4%D0%B0/createFood_1
6) получить отчет за определенный день http://localhost:8082/swagger-ui/index.html#/%D0%9A%D0%BE%D0%BD%D1%82%D1%80%D0%BE%D0%BB%D0%BB%D0%B5%D1%80%20%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D0%B5%D0%BB%D1%8F/getCaloriesStatisticByUser
7) получить отчет за несколько дней http://localhost:8082/swagger-ui/index.html#/%D0%9A%D0%BE%D0%BD%D1%82%D1%80%D0%BE%D0%BB%D0%BB%D0%B5%D1%80%20%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D0%B5%D0%BB%D1%8F/getCaloriesStatisticByUserForDays

