import store from '@/store'

/**
 * Character permission check
 * @param {Array} value Calibration value
 * @returns {Boolean}
 */
export function checkPermi(value) {
    if (value && value instanceof Array && value.length > 0) {
        const permissions = store.getters && store.getters.permissions
        const permissionDatas = value
        const all_permission = "*:*:*"; // Administrator rights
        const hasPermission = permissions.some(permission => {
            return all_permission === permission || permissionDatas.includes(permission)
        })

        if (!hasPermission) {
            return false
        }
        return true
    } else {
        console.error(`need roles! Like checkPermi="['system:user:add','system:user:edit']"`)
        return false
    }
}

/**
 * Role Permission Verification
 * @param {Array} value Calibration value
 * @returns {Boolean}
 */
export function checkRole(value) {
    if (value && value instanceof Array && value.length > 0) {
        const roles = store.getters && store.getters.roles
        const permissionRoles = value

        const hasRole = roles.some(role => {
            return permissionRoles.includes(role)
        })

        if (!hasRole) {
            return false
        }
        return true
    } else {
        console.error(`need roles! Like checkRole="['admin','editor']"`)
        return false
    }
}
