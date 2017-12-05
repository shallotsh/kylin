var webpack = require('webpack');

/*
 * Default webpack configuration for development
 */
var config = {
    devtool: 'eval-source-map',
    entry: __dirname + "/source/App.js",
    output: {
        path: __dirname + "/public/js",
        filename: "arrange.js"
    },
    module: {
        loaders: [{
            test: /\.jsx?$/,
            exclude: /node_modules/,
            loader: 'babel',
            query: {
                presets: ['es2015', 'react']
            }
        }]
    },
    devServer: {
        contentBase: "./public",
        colors: true,
        historyApiFallback: true,
        inline: true
    },
    // plugins: [
    //     new webpack.DefinePlugin({ // <-- 减少 React 大小的关键
    //         'process.env': {
    //             'NODE_ENV': JSON.stringify('production')
    //         }
    //     }),
    //     new webpack.optimize.DedupePlugin(), //删除类似的重复代码
    //     new webpack.optimize.UglifyJsPlugin(), //最小化一切
    //     new webpack.optimize.AggressiveMergingPlugin()//合并块
    // ],
}

/*
 * If bundling for production, optimize output
 */
if (process.env.NODE_ENV === 'production') {
    config.devtool = false;
    config.plugins = [
        new webpack.optimize.OccurenceOrderPlugin(),
        new webpack.optimize.UglifyJsPlugin({comments: false}),
        new webpack.DefinePlugin({
            'process.env': {NODE_ENV: JSON.stringify('production')}
        })
    ];
}
;

module.exports = config;

