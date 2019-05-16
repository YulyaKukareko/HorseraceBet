let path = require('path');

module.exports = {
    entry: "./src/index.js",
    devtool: 'eval-source-map',
    output: {
        path: path.resolve(__dirname, '../server/src/main/webapp/resources'),
        publicPath: './',
        filename: "bundle.js",
    },
    watch: true,
    watchOptions: {
        poll: true,
        ignored: /node_modules/
    },
    module: {
        rules: [
            {
                test: /\.js?$/,
                exclude: /(node_modules)/,
                loader: "babel-loader",
                options: {
                    presets: ["@babel/preset-env", "@babel/preset-react"]
                }
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(png|svg|jpg|gif|woff2)$/,
                use: ["url-loader"]
            }
        ]
    }
};