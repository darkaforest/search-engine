#!/usr/bin/env python

import time

import scrapy

import psycopg2

import uuid

import re


class testSpider(scrapy.Spider):
    name = 'test'
    allowed_domains = ['news.ifeng.com', 'news.163.com', 'xinhuanet.com', 'chinanews.com']
    start_urls = ['http://news.ifeng.com', 'https://news.163.com/', 'http://www.xinhuanet.com', 'http://www.chinanews.com']
    got_urls = []

    def __init__(self):
        self.conn = psycopg2.connect(database="search-engine", user="root", password="local-test-pwd", host="127.0.0.1", port="5432")
        self.cursor = self.conn.cursor()
        self.depth = 0
        self.num = 0
        self.save_ddl = 100

    def __del__(self):
        self.conn.commit()
        self.cursor.close()
        self.conn.close()
        print 'crawler done'

    def parse(self, response):
        self.save_ddl -= 1
        if self.save_ddl == 0:
            self.conn.commit()
            print 'commit while running'
            self.save_ddl = 100
        if self.num >= 500000:
            self.conn.commit()
            print 'crawler done'
            self.cursor.close()
            self.conn.close()
            return
        self.depth += 1
        self.num += 1
        print ('@count: %d @layer : %d @length : %d\n@url : %s' %
               (self.num, self.depth, len(response.text), response.url))
        self.cursor.execute('insert into s_raw_data (id, s_time, s_depth, s_length, s_url, s_title, s_content) values (%s, %s, %s, %s, %s, %s, %s)', (str(uuid.uuid1()), int(round(time.time() * 1000)), self.depth, len(response.body), response.url, re.sub('<[^<]+?>', '', response.xpath('//title').extract()[0])[0:128], response.text))
        next_urls = response.xpath('//a/@href').extract()
        for next_url in next_urls:
            if next_url.startswith('http'):
                yield scrapy.Request(next_url, callback=self.parse)
        self.depth -= 1
