import {
    login,
    logout,
    getInfo
} from '@/api/login'
import {
    getToken,
    setToken,
    removeToken
} from '@/utils/auth'
import {
    getNoReadNum
} from "@/api/index";
import Cookies from "js-cookie";

const user = {
    state: {
        token: getToken(),
        name: '',
        avatar: '',

        roles: [],
        permissions: [],
        //new
        remark: '',
        infonum: '',
    },

    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_NAME: (state, name) => {
            state.name = name
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles
        },
        SET_INFONUM: (state, infonum) => {
            state.infonum = infonum
        },

        SET_REMARK: (state, remark) => {
            state.remark = remark
        },
        SET_PERMISSIONS: (state, permissions) => {
            state.permissions = permissions
        },

        SET_ROLESNAME: (state, rolesname) => {
            state.rolesname = rolesname
        },
    },

    actions: {

        GetInfoNum({
            commit
        }) {
            return new Promise((resolve, reject) => {
                // getNoReadNum().then(res => {
                //
                //
                //     commit('SET_INFONUM', res.data)
                //     resolve()
                // }).catch(error => {
                //     reject(error)
                // })
            })
        },
        // The login
        Login({
            commit
        }, userInfo) {
            const username = userInfo.username.trim()
            const password = userInfo.password
            const code = userInfo.code
            const uuid = userInfo.uuid
            return new Promise((resolve, reject) => {
                login(username, password, code, uuid).then(res => {
                    setToken(res.token)
                    commit('SET_TOKEN', res.token)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // Obtaining User information
        GetInfo({
            commit,
            state
        }) {
            return new Promise((resolve, reject) => {
                getInfo(state.token).then(res => {
                    console.log(res)
                    Cookies.set("deptId", res.user.deptId, {
                        expires: 30
                    });
                    const user = res.user
                    const avatar = user.avatar == "" ? require("@/assets/image/profile.jpg") : process.env.VUE_APP_BASE_API + user.avatar;
                    if (res.roles && res.roles.length > 0) { // Validate returnedrolesIs a non-empty array
                        commit('SET_ROLES', res.roles)
                        commit('SET_PERMISSIONS', res.permissions)
                    } else {
                        commit('SET_ROLES', ['ROLE_DEFAULT'])
                    }

                    commit('SET_ROLESNAME', user.roles)
                    commit('SET_REMARK', user.remark)
                    commit('SET_NAME', user.nickName)
                    commit('SET_AVATAR', avatar)

                    resolve(res)
                }).catch(error => {
                    reject(error)
                })
            })
        },


        // Log out
        LogOut({
            commit,
            state
        }) {
            return new Promise((resolve, reject) => {
                logout(state.token).then(() => {
                    commit('SET_TOKEN', '')
                    commit('SET_ROLES', [])
                    commit('SET_PERMISSIONS', [])
                    removeToken()
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // The front end logout
        FedLogOut({
            commit
        }) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                removeToken()
                resolve()
            })
        }
    }
}

export default user
