Конфигурация — краткое руководство

Цель

Коротко: приведите локальные значения и один авторитетный источник конфигурации (локальный `application.yml` или Spring Cloud Config). Документ помогает разработчику быстро запустить сервисы и понять обязательные переменные.

Ключевые настройки (минимум)

- `rabbitmq.host`, `rabbitmq.port`, `rabbitmq.username`, `rabbitmq.password` — доступ к RabbitMQ.
- `rabbitmq.exchange.name`, `rabbitmq.routing.key`, `rabbitmq.queue.name` — обмен/маршруты для Activity → AI.
- `spring.data.mongodb.uri` (или `spring.data.mongodb.*`) — MongoDB для Recommendation/Activity.
- `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password` — для `userService` (если используется MySQL).
- `gemini.api.key` — API-ключ для Gemini/LLM (обязательно хранить в окружении, не в коде).
- `eureka.client.serviceUrl.defaultZone` — адрес Eureka (для сервисов, использующих регистрацию).
- `server.port` — порт приложения.

Как организовать источник конфигурации

Вариант A — простой локальный запуск (рекомендуется для разработки):
- Используйте `application.yml` в модуле сервиса (уже присутствует). Для секретов используйте переменные окружения, например `${GEMINI_API_KEY:}`.
- Поддерживайте шаблон `application.sample.yml` или `.env.example` в корне репозитория и не храните реальные секреты в файлах.

Вариант B — Spring Cloud Config (если репо решит это применять):
- Решение должно быть зафиксировано в ADR и в `README.md`.
- Если включаете Config Server, убедитесь, что сервисы содержат `spring.config.import: optional:configserver:` или `bootstrap.yml` с импортом, и удалите/архивируйте локальные копии, которые не используются.

Специфичные замечания (из аудита)

- Поле `rabbitmq.queue.name` используется в `activityService` и `AI_Service` — убедитесь, что producer и consumer используют одно и то же значение. Listener уже использует `${rabbitmq.queue.name}`.
- Вынесите изменяемые URL/Origin из Java-кода в конфигурацию (`WebClientConfig`, `SecurityConfig`).
- Для MongoDB проверьте вложенность ключей в `application.yml` — парсер YAML чувствителен к вложенности.

Минимальный локальный запуск (пример)

1) Создайте файл `.env` из `.env.example` и заполните реальные (локальные) значения для RabbitMQ и MongoDB.

2) Запустите зависимости (локально или через Docker Compose):

```bash
# пример (если у вас есть docker-compose.yml)
docker compose up -d mongodb rabbitmq
```

3) Запустите сервисы (в корне проекта):

```bash
# по модулям
cd activityService/activityService
./mvnw spring-boot:run

cd ../../AI_Service/AI_Service
./mvnw spring-boot:run
```

4) Проверка: отправьте простой Activity через API gateway или напрямую в `activityService` и проверьте, что `AI_Service` получает сообщение и сохраняет `Recommendation`.

Дальнейшие улучшения (скоро)

- Добавить CI-шаг, валидирующий синтаксис YAML и проверяющий присутствие обязательных ключей (например, в GitHub Actions). Это поможет обнаруживать рассинхронизацию конфигов на ранней стадии.
- Ввести `@ConfigurationProperties` и `@Validated` для важных секций конфигурации, чтобы приложение падало с понятной ошибкой при отсутствии обязательных значений.
- Создать `application-test.yml` и Testcontainers-набор для reproducible integration environment.
