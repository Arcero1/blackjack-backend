## BlackJack: An Application
This repository, along with its frontend counterpart found [here](https://github.com/Arcero1/blackjack-frontend),
is my submission for the QA Academy Individual Project.


## 1. INTRODUCTION
### 1a. The Brief
Create an OOP-based application with utilisation of supporting tools, methodologies and technologies that encapsulate all core modules covered during training.

### 1b. Motivation
The brief was to create a simple implementation of an online CRUD database. 
However, due to the open-ended nature of the project, coupled with 
the creator's distaste for data entry, a basic database was out of the question
before it was considered.
 
As throughout the first two weeks in the academy,
the creator had already created two separate "blackjack"-ish functions,
he considered himself something of an expert in blackjack.

Because of this, as well as a truly pitiful lack of online blackjack applications, 
the idea for this project was devised.

### 1c. The Solution
The main aim of the project was the creation of a full-stack implementation of an online game of blackjack.
Ultimately, such an implementation would be played just as blackjack would in a casino (save the gambling real money part).

## 2. THE PROJECT AT LARGE
### 2a. Architecture
![architecture diagram](docs/architecture.png)

### 2b. Database
![database](docs/database.png)

### 2c. Continuous Integration
![database](docs/jenkins.png)

## 3. BACKEND
![class diagram](docs/classdiagram.png)

###  3c. Testing
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html id="htmlId">
<head>
  <title>Coverage Report :: Summary</title>
  <style type="text/css">
    @import "./.css/coverage.css";
  </style>
</head>

<body>
<div class="header"></div>

<div class="content">
<div class="breadCrumbs">
   [ all classes ]
</div>

<h1>Overall Coverage Summary </h1>
<table class="coverageStats">
  <tr>
    <th class="name">Package</th>
<th class="coverageStat 
">
  Class, %
</th>
<th class="coverageStat 
">
  Method, %
</th>
<th class="coverageStat 
">
  Line, %
</th>
  </tr>
  <tr>
    <td class="name">all classes</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (28/ 28)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    76.7%
  </span>
  <span class="absValue">
    (99/ 129)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    77.4%
  </span>
  <span class="absValue">
    (281/ 363)
  </span>
</td>
  </tr>
</table>

<br/>
<h2>Coverage Breakdown</h2>

<table class="coverageStats">
  <tr>
    <th class="name  sortedAsc
">
<a href="index_SORT_BY_NAME_DESC.html">Package</a>    </th>
<th class="coverageStat 
">
  <a href="index_SORT_BY_CLASS.html">Class, %</a>
</th>
<th class="coverageStat 
">
  <a href="index_SORT_BY_METHOD.html">Method, %</a>
</th>
<th class="coverageStat 
">
  <a href="index_SORT_BY_LINE.html">Line, %</a>
</th>
  </tr>
  <tr>
    <td class="name"><a href="com.qa.blackjack/index.html">com.qa.blackjack</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/ 2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    66.7%
  </span>
  <span class="absValue">
    (2/ 3)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    50%
  </span>
  <span class="absValue">
    (2/ 4)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="com.qa.blackjack.account/index.html">com.qa.blackjack.account</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (5/ 5)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    72.3%
  </span>
  <span class="absValue">
    (34/ 47)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    61%
  </span>
  <span class="absValue">
    (75/ 123)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="com.qa.blackjack.exceptions/index.html">com.qa.blackjack.exceptions</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/ 2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/ 2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/ 2)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="com.qa.blackjack.game/index.html">com.qa.blackjack.game</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (6/ 6)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (27/ 27)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    95.2%
  </span>
  <span class="absValue">
    (99/ 104)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="com.qa.blackjack.profile/index.html">com.qa.blackjack.profile</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (5/ 5)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    58.8%
  </span>
  <span class="absValue">
    (20/ 34)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    68.4%
  </span>
  <span class="absValue">
    (54/ 79)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="com.qa.blackjack.response/index.html">com.qa.blackjack.response</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (6/ 6)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    92.3%
  </span>
  <span class="absValue">
    (12/ 13)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    96.9%
  </span>
  <span class="absValue">
    (31/ 32)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="com.qa.blackjack.util/index.html">com.qa.blackjack.util</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/ 2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    66.7%
  </span>
  <span class="absValue">
    (2/ 3)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    94.7%
  </span>
  <span class="absValue">
    (18/ 19)
  </span>
</td>
  </tr>
</table>
</div>

<div class="footer">
    
    <div style="float:right;">generated on 2019-09-26 17:43</div>
</div>
</body>
</html>

## 4. FRONTEND
### 4a. Visual Design
The product as of submission conforms quite closely to the design created at the beginning of the mockup process,
as evidenced by the wireframes below. This is largely because the project was largely an exercise in conforming to the design,
rather than adapting it to ability or coder preferences.

![wireframes](docs/wireframes.png)

The features that differ from the preliminary designs include:
* an expanded bet panel, due to the general emptyness of the screen when no cards are present
* no ranks are available as of yet for profiles and leaderboard
* the profile tab is fixed at the top, instead of being pushed to the bottom when the dashboard is open

The latter two are still planned features.

### 4b. Technical Design

### 4b. Testing

## 5. CONCLUSION
### 5a. The Overall Result

### 5b. Future Development

