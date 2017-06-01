# MyTools

把自己学习和使用的一些东西总结一下，代码大多是以前为了实现需求从网上找的，自己再进行优化改动。

目前工程包括两个部分
- app：tools部分的一些实例  
- tools：过去学习总结的一些工具:
    1. RecycleView的一些通用组件
        * 一个RecycleView的通用Adapter->RecycleViewAdapter
        * 一个RecycleView的通用ViewHolder->RecycleViewHolder  
        * 一个RecycleView的通用ItemDecoration->CommonItemDecoration,自动适配下面三个ItemDecoration
            * 一个GridLayoutManager用的ItemDecoration->GridDivider  
            * 一个GridLayoutManager用的StaggeredGridLayoutManager->StaggeredGridDivider  
            * 一个GridLayoutManager用的LinearLayoutManager->LinearDivider 
    2. 工具类
        * 一个用于快速toast的工具类
        * 一个精度值转换的工具类
    3. 自定义控件
        * 一个自定义的TitleBar以及采用Builder模式设计的设置器
        * 一个基于RecycleView和SwipeRefreshLayout的下拉刷新控件，上滑加载更多控件
        * 一个用于继承的悬浮框控件
