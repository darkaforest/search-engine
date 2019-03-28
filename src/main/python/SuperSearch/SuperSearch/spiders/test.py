#!/usr/bin/env python

import time

import scrapy

import mysql.connector

import uuid

import logging


class testSpider(scrapy.Spider):
    name = 'test'
    allowed_domains = ['news.sina.com.cn']
    start_urls = ['https://news.sina.com.cn/']
    got_urls = []

    def __init__(self):
        config = {
            'host': '127.0.0.1',
            'user': 'root',
            'password': 'local-test-pwd',
            'port': 3306,
            'database': 'super_search',
            'charset': 'utf8'
        }

        try:
            self.conn = mysql.connector.connect(**config)
        except mysql.connector.Error as e:
            print('connect fail! {}'.format(e))

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
        if self.num >= 100000:
            self.conn.commit()
            print 'crawler done'
            self.cursor.close()
            self.conn.close()
            return
        self.depth += 1
        self.num += 1
        print ('@count: %d @layer : %d @length : %d\n@url : %s' %
               (self.num, self.depth, len(response.body), response.url))
        try:
            self.cursor.execute('insert into s_raw_data (id, s_time, s_depth, s_length, s_url, s_title, s_content) values (%s, %s, %s, %s, %s, %s, %s)', (str(uuid.uuid1()), int(round(time.time() * 1000)), self.depth, len(response.body), response.url, response.xpath('//title').extract()[0][0:128], response.xpath('//body').extract()[0]))
        except mysql.connector.Error as e:
            print('insert fail! {}'.format(e))
        next_urls = response.xpath('//a/@href').extract()
        for next_url in next_urls:
            if next_url.startswith('http'):
                yield scrapy.Request(next_url, callback=self.parse)
        self.depth -= 1
