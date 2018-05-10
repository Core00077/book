# 书蕴后台
就酱
>迅猛发展的社交网络正深刻地改变着人们的学习、生活方式，其中网络书评对阅读选择也产生了不可忽视的影响。本项目拟利用网络书评的客观性，为读者推荐更符合个人偏好的书籍，而非依靠热门排行或基于用户行为进行推荐。本项目从书评文本内容出发，利用分类方法甄别有效评论、利用文本处理技术建立书籍的标签集、利用深度学习技术计算不同书籍的标签集之间的关联度，从而达到基于书评内容推荐书籍的目的。

[book.corechan.cn](mailto:i@corechan.cn)

接口请访问/swagger-ui.html

# 说明
使用spring boot开发，需要redis和mongodb环境，redis主要做session本地化缓存。

书籍相关度计算由python完成，训练word2vec模型。具体内容请参考[我的博客](https://blog.csdn.net/core00077)

后台还要开发个python后台提供实时搜索功能
# 作者
[i@corechan.cn](mailto:i@corechan.cn) 陈小睿

算法等相关的作者还有其他贡献者。

谢谢各位。