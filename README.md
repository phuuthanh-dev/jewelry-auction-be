[![Build Spring Boot Project](https://github.com/phuuthanh2003/AuctionWebApp_BE/actions/workflows/build.yml/badge.svg)](https://github.com/phuuthanh2003/AuctionWebApp_BE/actions/workflows/build.yml)
[![Release Auction REST API](https://github.com/phuuthanh2003/AuctionWebApp_BE/actions/workflows/release.yml/badge.svg)](https://github.com/phuuthanh2003/AuctionWebApp_BE/actions/workflows/release.yml) 
[![Publish Auction Backend Docker Image](https://github.com/phuuthanh2003/AuctionWebApp_BE/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/phuuthanh2003/AuctionWebApp_BE/actions/workflows/docker-publish.yml)
[![CodeFactor](https://www.codefactor.io/repository/github/phuuthanh2003/AuctionWebApp_BE/badge)](https://www.codefactor.io/repository/github/phuuthanh2003/AuctionWebApp_BE)
## Requirements

- JDK 17 or later
- SQL Server 2019
- Maven 3+

## How to Run
- Clone the repository: 
```bash
git clone https://github.com/phuuthanh2003/AuctionWebApp_BE.git
```
- Open in your preferred IDE.

- Navigate to the project directory:
```bash
cd AuctionWebApp_BE
```
- You can build the project and run the tests by running:
```bash
mvn clean package
```
- Once successfully built, you can run the service:
```bash
mvn spring-boot:run
```

### To view Swagger 3 API docs

Run the server and browse to https://auction-webapp-production.up.railway.app/swagger-ui/index.html

## Contributors

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="20%"><a  href="https://github.com/hardingadonis"><img src="https://avatars.githubusercontent.com/u/102614541?v=4" width="100px;" alt=""/><br /><sub><b>Phùng Hữu Thành</b></sub></a></td>
      <td align="center" valign="top" width="20%"><a href="https://github.com/bakaqc"><img src="https://avatars.githubusercontent.com/u/139938101?v=4" width="100px;" alt=""/><br /><sub><b>Huy Hoang</b></sub></a></td>
      <td align="center" valign="top" width="20%"><a href="https://github.com/htnghia1423"><img src="https://avatars.githubusercontent.com/u/155858724?v=4" width="100px;" alt=""/><br /><sub><b>Tâm</b></sub></a></td>
      <td align="center" valign="top" width="20%"><a href="https://www.codefactor.io"><img src="https://avatars.githubusercontent.com/u/13309880?v=4" width="100px;" alt=""/><br /><sub><b>Automated code reviews</b></sub></a></td>
      <td align="center" valign="top" width="20%"><a href="https://www.codefactor.io"><img src="https://avatars.githubusercontent.com/u/66716858?v=4" width="100px;" alt=""/><br /><sub><b>Railway</b></sub></a></td>
    </tr>
  </tbody>
</table>

## License & Copyright
&copy; 2024 Phung Huu Thanh Licensed under the [Apache License 2.0](https://github.com/phuuthanh2003/AuctionWebApp_BE/blob/main/LICENSE).