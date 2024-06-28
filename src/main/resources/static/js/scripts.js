/*!
* Start Bootstrap - Simple Sidebar v6.0.6 (https://startbootstrap.com/template/simple-sidebar)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-simple-sidebar/blob/master/LICENSE)
*/
// 
// Scripts
//
var logStat;
window.addEventListener('DOMContentLoaded', event => {

    $('#stnNmArea').hide();
    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }

        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

    const logOutButton = document.getElementById('logOut');

    if (logOutButton) {
        logOutButton.addEventListener('click', event => {
            function success() {
                localStorage.removeItem('access_token');
                console.log(deleteCookie('refresh_token'));
                console.log(deleteCookie('userEmail'));
                location.replace('/');
            }

            function fail() {
                alert("로그아웃에 실패하였습니다.");
            }

            httpRequest('DELETE', '/api/refresh-token', null, success, fail);
        });
    }

    if (getCookie("userEmail")) {

    }


    if (getCookie("refresh_token") != null) {
        logStat = false;
    } else {
        logStat = true;
    }
    if (logStat) {
        document.getElementById("logIn").style.display = "block";
        document.getElementById("logOut").style.display = "none";
        if (document.getElementById("moveWrite") != null) {
            document.getElementById("moveWrite").style.display = "none";
        }
    } else {
        document.getElementById("logIn").style.display = "none";
        document.getElementById("logOut").style.display = "block";
        if (document.getElementById("moveWrite") != null) {
            document.getElementById("moveWrite").style.display = "block";
        }
    }

});

function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(";");
    cookie.some(function (item) {

        item = item.replace(" ", "");

        var dic = item.split("=");

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });
    return result;
}

function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: {
            Authorization: "Bearer " + localStorage.getItem("access_token"),
            "Content-Type": "application/json",
        },
        body: body,
    }).then((response) => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie("refresh_token");
        if (response.status === 401 && refresh_token) {
            fetch("/api/token", {
                method: "POST",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("access_token"),
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    refreshToken: getCookie("refresh_token"),
                }),
            })
                .then((res) => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then((result) => {
                    localStorage.setItem("access_token", result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch((error) => fail());
        } else {
            return fail();
        }
    });

}

const token = searchParam('token');

if (token) {
    localStorage.setItem("access_token", token);
}

function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}


function deleteCookie(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}


const createButton = document.getElementById("write-btn");

if (createButton) {
    createButton.addEventListener("click", (event) => {
        body = JSON.stringify({
            title: document.getElementById("title").value,
            content: document.getElementById("content").value,
        });

        function success() {
            alert("등록되었습니다.");
            location.replace("/board/list");
        }

        function fail() {
            alert("등록 실패했습니다.");
            location.replace("/board/list");
        }

        httpRequest("POST", "/board/write", body, success, fail);
    });
}

function moveWrite() {
    location.href = "/board/write";
}

var searchStn = () => {
    var stnNm = $("#stdStation").val();
    if (stnNm === null) {
        alert("역명을 입력해주세요");
    }
    $.ajax({
        type: "post",
        url: "stnm",
        data: {
            stnNm: stnNm
        },
        success: (fragment) => {
            $('#stnNmArea').replaceWith(fragment);
            $('#stnNmArea').show();
        },
        error: (error) => {
        },
    });
}

function changeStation(){
    var selectStnNm = $("select option:selected").val();
    console.log(selectStnNm);
    document.getElementById("stdStation").value = selectStnNm;
}