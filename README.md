********文档说明***********
answer.txt 一份正确的分词结果
dic.txt 把语料库的词选取出来后单行排列
essay.txt 一份未分词的原文

FmmResult.txt 正向最大匹配结果
BmmResult.txt 逆向最大匹配结果
ShortestPahResult.txt 最短路径匹配结果

*******java文件说明*******
FmmMatch.java 正向匹配的主程序
BmmMatch.java 逆向匹配的主程序
ShortestPath.java 最短路径匹配主程序
Compare.java 正确率计算主程序

BufferRead.java 缓冲读入文件
CodeConversion.java Unicode编码字符串与中文字符串的转换类
DictionaryConstructor.java 读入文件，建立Trie树
Graph.java 图类
MyTrie.java Trie树类
Node.java 节点类

*********其它说明***********
匹配所用和所输出的文件均为utf-8格式，并且行尾格式为 Unix/Linux，如果answer.txt（正确分词答案文件）和匹配输出的文件格式不一样，就会出错……

