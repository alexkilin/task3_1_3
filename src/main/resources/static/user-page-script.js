$(document).ready(
    function () {
        getCurrentUser();
        getUserInfo();
    }
)


//get current user
function getCurrentUser() {
    $.ajax({
        method: "GET",
        url: "/user/getCurrentUser",
        success: function (result) {
            $('#current_username').text(result.username + "\u00A0");
            var corrent_roles = " with roles: ";
            $.each(result.roles, function (i, role) {
                corrent_roles = corrent_roles + role.role.substring(5) + " ";
            })
            $('#current_roles').text(corrent_roles);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}


function getUserInfo() {
    $.ajax({
        method: "GET",
        url: "/user/getCurrentUser",
        success: function (result) {

            var newRows = `<tr><td>${result.id}</td><td>${result.firstName}</td><td>${result.lastName}</td>` +
                `<td>{$result.age}</td><td>${result.email}</td><td>${result.username}</td><td>`;
            $.each(result.roles, function (i, role) {
                newRows = newRows + `<normal>${role.role.substring(5)}</normal>` + " "
            });
            newRows=newRows + "</tr>";
            $('#userInfo').empty();
            $('#userInfo').append(newRows);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}

