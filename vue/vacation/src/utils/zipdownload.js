import axios from 'axios'
import { getToken } from '@/utils/auth'

const mimeMap = {
    xlsx: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    zip: 'application/zip'
}

const baseUrl = process.env.VUE_APP_BASE_API
    // const baseURL = 'http://47.101.183.57:8080'
export function downLoadZip(str, filename) {
    var url = baseUrl + str
    axios({
        method: 'get',
        url: url,
        responseType: 'blob',
        headers: { 'Authorization': 'Bearer ' + getToken() }
    }).then(res => {
        resolveBlob(res, mimeMap.zip)
    })
}
/**
 * parsingblobRespond to the content and download
 * @param {*} res blobResponse content
 * @param {String} mimeType MIMEtype
 */
export function resolveBlob(res, mimeType) {
    const aLink = document.createElement('a')
    var blob = new Blob([res.data], { type: mimeType })
        // //fromresponsetheheadersTo derivefilename, The back-endresponse.setHeader("Content-disposition", "attachment; filename=xxxx.docx") 设置the文件名;
    var patt = new RegExp('filename=([^;]+\\.[^\\.;]+);*')
    var contentDisposition = decodeURI(res.headers['content-disposition'])
    var result = patt.exec(contentDisposition)
    var fileName = result[1]
    fileName = fileName.replace(/\"/g, '')
    aLink.href = URL.createObjectURL(blob)
    aLink.setAttribute('download', fileName) // Set the download file name
    document.body.appendChild(aLink)
    aLink.click()
    document.body.appendChild(aLink)
}