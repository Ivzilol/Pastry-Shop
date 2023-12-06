import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import baseURL from "../BaseURL/BaseURL";

const AdminUsers = () => {
    const user = useUser();
    const [users, setUsers] = useState("");


    useEffect(() => {
        ajax(`${baseURL}api/users/admin`, "GET", user.jwt)
            .then(userData => {
                setUsers(userData);
            })

    }, [user.jwt])

    function refreshPage() {
        window.location.reload();
    }

    function deleteUser(id) {
        ajax(`${baseURL}api/users/admin/${id}`, "DELETE", user.jwt)
            .then(() => {
                refreshPage()
            })
    }

    function promoteUser(id) {
        ajax(`${baseURL}api/users/admin/promote/${id}`, "PATCH", user.jwt)
            .then((response) => {
                if (response.custom === 'Successful Promote') {
                    refreshPage()
                }
            })
    }

    return (
        <section className="admin-users-container">
            <NavBarAdmin/>
            <h3 className="admin-users-container-title">List Users</h3>
            <hr/>
            <div className="admin-users-container-header">
                <table className="admin-users-table">
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>Actions</th>
                    </tr>
                    {users ? (
                        <>
                            {users.map(user => (
                                <tr id={user.id}>
                                    <td>{user.id}</td>
                                    <td>{user.username}</td>
                                    <td>{user.firstName}</td>
                                    <td>{user.lastName}</td>
                                    <td>{user.email}</td>
                                    <td>{user.address}</td>
                                    <td className="admin-users-table-buttons">
                                        <button className="delete-button"
                                                onClick={() => deleteUser(user.id)}
                                        >Изтрий
                                        </button>
                                        <button className="promote-button"
                                                onClick={() => promoteUser(user.id)}
                                        >Направи админ
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </>
                    ) : (
                        <></>
                    )}
                </table>
            </div>
        </section>
    )
}

export default AdminUsers;