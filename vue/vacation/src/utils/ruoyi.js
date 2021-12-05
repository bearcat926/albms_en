/**
 * generaljsMethod encapsulation processing
 * Copyright (c) 2019 ruoyi
 */

const baseURL = process.env.VUE_APP_BASE_API
    // const baseURL = 'http://47.101.183.57:8080'

// Date formatting
export function parseTime(time, pattern) {
    if (arguments.length === 0 || !time) {
        return null
    }
    const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
    let date
    if (typeof time === 'object') {
        date = time
    } else {
        if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
            time = parseInt(time)
        } else if (typeof time === 'string') {
            time = time.replace(new RegExp(/-/gm), '/');
        }
        if ((typeof time === 'number') && (time.toString().length === 10)) {
            time = time * 1000
        }
        date = new Date(time)
    }
    const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay()
    }
    const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
        let value = formatObj[key]
            // Note: getDay() returns 0 on Sunday
        if (key === 'a') { return ['day', 'one', 'two', 'three', 'four', 'five', 'six'][value] }
        if (result.length > 0 && value < 10) {
            value = '0' + value
        }
        return value || 0
    })
    return time_str
}

// Reset the form
export function resetForm(refName) {
    if (this.$refs[refName]) {
        this.$refs[refName].resetFields();
    }
}

// Add date range
export function addDateRange(params, dateRange) {
    var search = params;
    search.beginTime = "";
    search.endTime = "";
    if (null != dateRange && '' != dateRange) {
        search.beginTime = this.dateRange[0];
        search.endTime = this.dateRange[1];
    }
    return search;
}

// The data dictionary is displayed
export function selectDictLabel(datas, value) {
    var actions = [];
    Object.keys(datas).map((key) => {
        if (datas[key].dictValue == ('' + value)) {
            actions.push(datas[key].dictLabel);
            return false;
        }
    })
    return actions.join('');
}

// Common download method
export function download(fileName) {
    window.location.href = baseURL + "/common/download?fileName=" + encodeURI(fileName) + "&delete=" + true;
}

// String formatting(%s )
export function sprintf(str) {
    var args = arguments,
        flag = true,
        i = 1;
    str = str.replace(/%s/g, function() {
        var arg = args[i++];
        if (typeof arg === 'undefined') {
            flag = false;
            return '';
        }
        return arg;
    });
    return flag ? str : '';
}

// Conversion string,undefined,nullSuch as into""
export function praseStrEmpty(str) {
    if (!str || str == "undefined" || str == "null") {
        return "";
    }
    return str;
}

/**
 * Construct the tree structure data
 * @param {*} data The data source
 * @param {*} id idfield The default 'id'
 * @param {*} parentId Parent node field The default 'parentId'
 * @param {*} children Child node field The default 'children'
 * @param {*} rootId The rootId The default 0
 */
export function handleTree(data, id, parentId, children, rootId) {
    id = id || 'id'
    parentId = parentId || 'parentId'
    children = children || 'children'
    rootId = rootId || 0
        //Deep clone source data
    const cloneData = JSON.parse(JSON.stringify(data))
        //Loop through all items
    const treeData = cloneData.filter(father => {
        let branchArr = cloneData.filter(child => {
            //Returns a subseries set for each term
            return father[id] === child[parentId]
        });
        branchArr.length > 0 ? father.children = branchArr : '';
        //Return to the first layer
        return father[parentId] === rootId;
    });
    return treeData != '' ? treeData : data;
}