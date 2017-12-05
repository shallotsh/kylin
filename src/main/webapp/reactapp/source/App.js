import React, {Component} from 'react';
import {render} from 'react-dom';

class App extends Component {
    render() {
        return (
            <section>
                <div id="page-inner">
                    {/*input sequence */}
                    <div className="row">
                        <div className="col-sm-2">
                            <input type="text" className="form-control" placeholder="序列1"/>
                        </div>
                        <div className="col-sm-2">
                            <input type="text" className="form-control" placeholder="序列2"/>
                        </div>
                        <div className="col-sm-2">
                            <input type="text" className="form-control" placeholder="序列3"/>
                        </div>
                        <div className="col-sm-2">
                            <input type="text" className="form-control" placeholder="序列4"/>
                        </div>
                        <div className="col-sm-2 col-sm-push-1">
                            <button type="button" className="btn btn-primary fill_parent"> 组 码</button>
                        </div>
                    </div>
                    {/*operate for transfer */}
                    <div className="row border-dashed-vertical">
                        <div className=" form-inline col-sm-3">
                            <label for="sum_code" className="control-label">和杀</label>
                            <input type="text" size="12" className="form-control fill_parent_50" id="sum_code"
                                   placeholder="和杀码"/>
                        </div>
                        <div className=" form-inline col-sm-3">
                            <label for="bold_code" className="control-label">胆杀</label>
                            <input type="text" size="12" className="form-control fill_parent_50" id="bold_code"
                                   placeholder="胆杀码"/>
                        </div>
                        <div className="col-sm-2">
                            <button type="button" className="btn btn-primary fill_parent"> 杀 码</button>
                        </div>
                        <div className="col-sm-2">
                            <button type="button" className="btn btn-primary fill_parent"> 转直选</button>
                        </div>
                        <div className="col-sm-2">
                            <button type="button" className="btn btn-primary fill_parent"> 排五</button>
                        </div>
                    </div>
                    <div className="row  content_align_center border-dashed-bottom">
                        <div className="col-sm-3">
                            <button type="button" className="btn btn-success fill_parent_50"> 导出</button>
                        </div>
                        <div className="col-sm-9 div-padding-right-none">
                            <textarea className="form-control" placeholder="操作历史记录" rows="2"></textarea>
                        </div>
                    </div>
                    <div className="row ">
                        <div className="col-sm-3 content_align_center">
                            <div className="row">
                                <div className="row">
                                    <div className="col-sm-6">
                                        <button type="button" className="btn btn-info fill_parent"> 小和杀</button>
                                    </div>
                                    <div className="col-sm-6">
                                        <button type="button" className="btn btn-info fill_parent"> 含三杀</button>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-sm-6">
                                        <button type="button" className="btn btn-info fill_parent"> 大和杀</button>
                                    </div>
                                    <div className="col-sm-6">
                                        <button type="button" className="btn btn-info fill_parent"> 含四杀</button>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-sm-6">
                                        <button type="button" className="btn btn-info fill_parent"> 奇偶杀</button>
                                    </div>
                                    <div className="col-sm-6">
                                        <button type="button" className="btn btn-info fill_parent"> 含五杀</button>
                                    </div>
                                </div>
                            </div>
                            <div className="row">
                                <div className="row">
                                    <div className="col-sm-12">
                                        <label for="myriabit">万</label>
                                        <input type="text" className="form-inline" id="myriabit" placeholder="万位"/>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-sm-12">
                                        <label for="kilobit">千</label>
                                        <input type="text" className="form-inline" id="kilobit" placeholder="千位"/>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-sm-12">
                                        <label for="hundred">百</label>
                                        <input type="text" className="form-inline" id="hundred" placeholder="百位"/>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-sm-12">
                                        <label for="decade">十</label>
                                        <input type="text" className="form-inline" id="decade" placeholder="十位"/>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-sm-12">
                                        <label for="unit">个</label>
                                        <input type="text" className="form-inline" id="unit" placeholder="个位"/>
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="col-sm-12">
                                        <button type="button" className="btn btn-info fill_parent_50"> 位 杀</button>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div className="col-sm-9 ">
                            <div className="row right-div-padding div-margin-bottom-clear">
                                <div>
                                    <span>这一行是统计数据展示区域</span>
                                </div>
                            </div>
                            <div className="row right-div-padding div-margin-top-clear">
                                <div className="border-solid code-show">
                                    3D码展示区域
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="row">
                        <div className="footer content_align_center">
                            <span>Copyright@2017</span>
                        </div>
                    </div>
                </div>
            </section>
        );
    }
}

render(<App/>, document.getElementById('page-wrapper'));