module.exports = {
    title: 'Vacation reservation management system',

    /**
     * Whether the system layout is configured
     */
    showSettings: false,

    /**
     * Whether or not shown tagsView
     */
    tagsView: true,

    /**
     * Fixed head or not
     */
    fixedHeader: false,

    /**
     * Whether or not shownlogo
     */
    sidebarLogo: true,

    /**
     * @type {string | array} 'production' | ['production', 'development']
     * @description Need show err logs component.
     * The default is only used in the production env
     * If you want to also use it in dev, you can pass ['production', 'development']
     */
    errorLog: 'production'
}
