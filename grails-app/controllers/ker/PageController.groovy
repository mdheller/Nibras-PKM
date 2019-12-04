/*
 * Copyright (c) 2018. Mohamad F. Fakih (mail@khuta.org)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */


package ker

import grails.util.Environment
import mcs.Book
import grails.web.JSONBuilder
//import org.eclipse.mylyn.wikitext.core.parser.MarkupParser
//import org.eclipse.mylyn.wikitext.markdown.core.MarkdownLanguage

import grails.plugin.springsecurity.annotation.Secured



@Secured('ROLE_ADMIN')
class PageController {

    static entityMapping = [
            'G'  : 'mcs.Goal',
            'T'  : 'mcs.Task',
            'P'  : 'mcs.Planner',

            'W'  : 'mcs.Writing',
            'N'  : 'app.IndexCard',

            'J'  : 'mcs.Journal',
            'I'  : 'app.IndicatorData',
            'K'  : 'app.Indicator',

            'Q'  : 'app.Payment',
            'L'  : 'app.PaymentCategory',

            'R'  : 'mcs.Book',
            'C'  : 'mcs.Course',
            'D'  : 'mcs.Department',
            'E'  : 'mcs.Excerpt',
            'S'  : 'app.Contact',
            'Tag': 'app.Tag',

            'Y'  : 'cmn.Setting',
            'X'  : 'mcs.parameters.SavedSearch'
    ]

    static allClasses = [mcs.Task, mcs.Goal, mcs.Writing, app.IndexCard,
                         app.Payment, app.IndicatorData,
                         mcs.Journal, mcs.Planner, mcs.Book]

    static recentClasses = [mcs.Goal, mcs.Task, mcs.Planner, mcs.Journal, mcs.Writing, app.IndexCard,
                            mcs.Book]


    def legacyAdd() {
        render(template: '/layouts/legacyAdd')
    }

    def help() {
        render(template: '/page/help')
    }
// def main2() {
//        render(view: '/page/main2')
//    }


    def main() {

        if (cmn.Setting.count() == 0) {
            def r
            [
                    ['mcs.parameters.GoalType', 'Done', 'done'],
                    ['mcs.parameters.Context', 'Home', 'home'],
                    ['mcs.parameters.Context', 'work', 'Work'],
                    ['mcs.parameters.WorkStatus', 'Done', 'done'],
                    ['mcs.parameters.WorkStatus', 'Dismissed', 'dismissed'],
                    ['mcs.parameters.PlannerType', 'Assign', 'assign'],
                    ['mcs.parameters.JournalType', 'Event', 'event'],
                    ['mcs.parameters.JournalType', 'Summary', 'summary'],
                    ['mcs.parameters.ResourceStatus', 'Read', 'read'],
                    ['mcs.parameters.ResourceStatus', 'Core', 'core'],
                    ['app.parameters.ResourceType', 'Various', 'various'],
                    ['app.parameters.ResourceType', 'Audio', 'audio'],
                    ['app.parameters.ResourceType', 'Video', 'video'],
                    ['app.parameters.ResourceType', 'Document', 'doc'],
                    ['app.parameters.ResourceType', 'Article', 'article'],
                    ['app.parameters.ResourceType', 'Book', 'book'],
                    ['mcs.parameters.WritingStatus', 'Revised', 'revised'],
                    ['mcs.parameters.WritingType', 'Unsorted', 'usr'],
                    ['mcs.parameters.WritingType', 'Quotation', 'quote'],
                    ['app.PaymentCategory', 'bill', 'Bill'],
                    ['app.PaymentCategory', 'food', 'Food'],
                    ['app.PaymentCategory', 'cloth', 'cloth'],
                    ['app.PaymentCategory', 'transp', 'Transportation']
            ].each() { d ->
                r = grailsApplication.classLoader.loadClass(d[0]).newInstance()
                r.name = d[1]
                r.code = d[2]
                r.save(flush: true)
            }


            [
                    ['app.parameters.CommandPrefix', 'Add goals (one per line)', ''],
                    ['app.parameters.CommandPrefix', 'Add tasks (one per line) a t -- ', ''],
                    ['app.parameters.CommandPrefix', 'Add tasks (one per line) a t -- ', ''],
                    ['app.parameters.CommandPrefix', 'Command', 'a j #summary  (?date -- ']
            ].each() { d ->
                new app.parameters.CommandPrefix([summary: d[1], description: d[2], multiLine: true]).save(flush: true)
            }




            def s
            [
                    ['All journal', 'J', 'Jcal', 'from Journal t where id > 0', 'select count(*) from Journal', 1],
                    ['All plans', 'P', 'Pcal', 'from Planner t where id > 0', 'select count(*) from Planner', 1],
                    ['All tasks with due dates', 'T', 'Tcal', 'from Task t where endDate is not null', 'select count(*) from Task t where endDate is not null', 1],
                    ['All tasks', 'T', 'Tall', 'from Task t where id > 0', 'select count(*) from Task t', 0],
                    ['All notes', 'N', 'Nall', 'from IndexCard t where id > 0', 'select count(*) from IndexCard t', 0],
                    ['All resources', 'R', 'Rall', 'from Book t where id > 0', 'select count(*) from Book t', 0],
                    ['All department', 'D', 'Dall', 'from Department t where id > 0', 'select count(*) from Department t', 0],
                    ['All courses', 'C', 'Call', 'from Course t where id > 0', 'select count(*) from Course t', 0]
            ].each() { d ->
                s = new mcs.parameters.SavedSearch()
                s.entity = d[1]
                s.summary = d[0]
                s.query = d[3]
                s.countQuery = d[4]
                s.queryType = 'hql'

                if (d[5] == 1)
                    s.calendarEnabled = true

                s.save(flush: true)
            }




            [['app.URL', 'http://localhost:2019/nibras'],
             ['description.summarize.threshold', '180'],
             ['summary.summarize.threshold', '160'],
             ['description.summarize.thresholdMax', '180'],
             ['description.style', "padding: 3px; font-size: 20px; font-family: 'Traditional Arabic', tahoma; margin: 5px; line-height: 25px; text-align: justify; color: black;"],
             ['recordPage.text.style', "color: black; padding: 30px; font-size: 17px !important; font-family: 'tahoma,Georgia,serif'; font-weight: normal; margin: 60px; line-height: 1.9; max-width: 700px;text-align: justify;"],
             ['root.rps1.path', 'D:/Nibras/Repository/'],
             ['root.rps2.path', ''],
             ['root.rps3.path', ''],
             ['tmp.path', 'd:/Nibras/Temporary'],
             ['planner.enabled', 'no'],
             ['journal.enabled', 'yes'],
             ['indicators.enabled', 'no'],
             ['payments.enabled', 'no'],
             ['writings.enabled', 'no'],
             ['notes.enabled', 'yes'],
             ['contacts.enabled', 'no'],
             ['excerpts.enabled', 'no'],
             ['goals.enabled', 'no'],
             ['tasks.enabled', 'yes'],
             ['resources.enabled', 'yes'],
             ['fullCalendar.enabled', 'yes'],
             ['rangeCalendar.enabled', 'no'],
             ['coursePanel.enabled', 'yes'],
             ['commandBar.enabled', 'yes'],
             ['enable.kanban', 'yes'],
             ['enable.fullCalendar', 'yes'],
             ['import.enabled', 'yes'],
             ['coursesPanel.enabled', 'yes'],
             ['courses.enabled', 'yes'],
             ['dashboard.enabled', 'yes'],
             ['course.enabled', 'yes'],
             ['app.name', 'Nibras PKM'],
             ['explorer.path.win', 'd:/Nibras/Application/scripts/open-explorer.bat'],
             ['explorer.path.linux', '/usr/bin/dolphin'],
             ['datetime.format', 'dd.MM.yyyy'],
             ['default.WritingStatus.done', 'revised'],
             ['default.WorkStatus.done', 'done'],
             ['default.ResourceStatus.done', 'read'],
             ['default.language', 'en'],
             ['updateResultSet.max-items', '100'],
             ['accordion.east.default.panel', '1'],
             ['accordion.west.default.panel', '1'],
             ['planner.homepage.default-type', 'knb'],
             ['repository.languages', 'ar,fa,fr,en'],
             ['repository.languages.RTL', 'ar,fa'],
             ['repository.languages.LTR', 'fr,en']
            ].each() {
                new cmn.Setting([name: it[0], value: it[1]]).save(flush: true)
            }

        }
//        def environment
//        switch (Environment.current) {
//            case Environment.PRODUCTION:
//                environment = 'prod'
//                break
//            case Environment.DEVELOPMENT:
//                environment = 'dev'
//                break
//        }
//
//        environment = Environment.current.name

//        def wrtCount = 0
//        mcs.Writing.findAllByIsLatex(true).each() {
//            wrtCount += it.description?.count(' ') ?: 0 //length() ?: 0
//        }

//        def recentRecords = []
//        recentClasses.each() {
//            recentRecords += it.findAllByDateCreatedGreaterThanAndDeletedOnIsNull(new Date() - 1, [sort: 'dateCreated', order: 'desc', max: 50])
//        }

//        def filledInDates = ''
//        Journal.executeQuery("select  DATE_FORMAT(startDate, '%c/%e/%Y') from Journal group by date(startDate) order by startDate asc").each() {
//            filledInDates += it + ' '
//        }

//        session['log'] = 1
        session['J'] = 1
        session['P'] = 1

        session['showLine1Only'] = 'on'
        session['showFullCard'] = 'off'



//        MarkupParser markupParser = new MarkupParser();
//        markupParser.setMarkupLanguage(new MarkdownLanguage());
//        String htmlContent = markupParser.parseToHtml(text);

        def r = ''
        def c = 0
//        def f = ker.OperationController.getPath('editBox.path')
//        if (f) {
//            new File(f)?.listFiles().each(){
//                if (it.isFile() && it.name?.endsWith('.md'))
//                    c++
////                if (it.isDirectory() && it.name.length() > 1)
////                    c++
//            }
//        }

        def resources =
                Book.executeQuery('from Book r where r.course.bookmarked = ? ' +
//                        in ' +
//                        '(select course from Planner p where p.startDate < current_date and p.endDate > current_date and p.course is not null)' +
                        ' and r.priority >= ? and r.readOn is not null' +
                        ' and (r.lastReviewed < ? or r.lastReviewed is null) and r.reviewCount < ?' +
                        ' order by r.orderNumber asc, r.reviewCount asc',
                        [true, 3, new Date() + 7, 5])
        def excerpts =
        Book.executeQuery('from Excerpt r where r.book.course.bookmarked = ? ' +
//                ' in' +
//                ' (select course from Planner p where p.startDate < current_date and p.endDate > current_date and p.course is not null)' +
                ' and r.priority >= ? and r.readOn is not null' +
                ' and (r.lastReviewed < ? or r.lastReviewed is null) and r.reviewCount < ?' +
                ' order by r.orderNumber asc, r.reviewCount asc',
                [true, 3, new Date() + 7, 5])

//        println 'r ' + resources
//        println 'e ' + excerpts


        render(view: '/appMain/main', model: [
                htmlContent: null,
                selectBasketCount: GenericsController.selectedRecords.size(),
                editFileCount: c,
                reviewPileSize: resources.size() + excerpts.size()
//                environment: environment
                //,
//                filledInDates: filledInDates?.trim(),
                // wrtCount: wrtCount, recentRecords: recentRecords

        ])
    }

    def appMihrab() {
        render(view: '/appMihrab/main', model: [
        ])
    }

    def appCourse() {
        render(view: '/appCourse/main', model: [ record: mcs.Course.get(params.id)
        ])
    }

    def appDaftar(){
        render(view: '/appMain/daftar', model: [])
    }
  def appCalendar() {
        render(view: '/appCalendar/main', model: [
        ])
    }

    def record() {
        render(view: '/page/record', model: [record:
                                                     grailsApplication.classLoader.loadClass(entityMapping[params.entityCode]).get(params.id)
        ])
    }

    def panel() {
        def record = grailsApplication.classLoader.loadClass(entityMapping[params.entityCode]).get(params.id)

        def typeSandboxPath
        if (record.entityCode() == 'R') {
            typeSandboxPath = OperationController.getPath('root.rps1.path') + 'R/' + record.type.code + '/' + (record.id / 100).toInteger() + '/' + record.id
        } else {
            typeSandboxPath = OperationController.getPath('root.rps1.path') + '' + params.entityCode + '/' + record.id
        }

        def source = ''
        def authors = ''
        if (record) {
            if (record.entityCode() == 'N' ||  record.entityCode() == 'W') {
                java.util.regex.Matcher matcher = record.description =~ /@([\S_-]*) /

                matcher.eachWithIndex() { match, i ->
                    source += mcs.Book.findByCode(match[1]?.replace(']','')) ?
                            ''//('Source found: ' + mcs.Book.findByCode(match[1]) + '<br/>')
                            :
                            ('Source not found: ' + match[1]) + '<br/>'
                }
            }
            def finalText
            /*
            if (record.entityCode() == 'W') {
                java.util.regex.Matcher matcher = record.description =~ /\}\{([\S\_-]*)\}/
                def book
                finalText = record.description
                matcher.eachWithIndex() { match, i ->
                    println 'match ' + match[1]
                    book = mcs.Book.findByCode(match[1])
                    if (book) {
                        authors += 'f r =' + match[1] + '<br/>'// + book.authorInBib + '<br/>'
                        finalText = finalText.replace('{' + match[1] + '}', '{' + book.authorInBib + '}')
                    }
                    else authors += ('Author not found: ' + match[1] + '<br/>')
                }
                //r.description = finalText
              new File('E:/wrt/w-ioa.tmp').write(finalText, 'UTF-8')
            }
            */

            render(template: '/layouts/panel', model: [entityCode: params.entityCode, record: record, mobileView: params.mobileView, typeSandboxPath: typeSandboxPath,
                                                    source    : source, authors: authors
            ])
        } else render 'Record not found'
    }

    def publish() {
        render(view: '/page/publish', model: [record:
                                                      grailsApplication.classLoader.loadClass(entityMapping[params.entityCode]).get(params.id)
        ])
    }

    def presentation() {
        render(view: '/page/presentation', model: [record:
                                                           grailsApplication.classLoader.loadClass(entityMapping[params.entityCode]).get(params.id)
        ])
    }


    def indicators() {
        render(view: '/page/indicators', model: [])
    }

    def calendar() {

        render(view: '/reports/calendar', model: [savedSearchId: params.id])
    }

//    ResourceLocator grailsResourceLocator // injected during initialization


        def slides() {

//            def resource = grailsResourceLocator.findResourceForURI('/css/chosen.css')
//            def path = resource.file.path // absolute file path
//            def resource = this.class.classLoader.getResource('chosen.css')
//            def path = resource.file // absolute file path
//            def inputStream = resource.inputStream // input stream for the file
//                             println 'chosen path'  + path + '\n'// + new File(path).text

            if (ker.OperationController.getPath('slides.path') && new File(ker.OperationController.getPath('slides.path')).exists() ) {
                def f = new File(ker.OperationController.getPath('slides.path') + '/contents.md')
                def t = ''
                app.IndexCard.findAllByMainHighlightsIsNotNull().each() {
                    t += it.mainHighlights + '\n---\n'
                }
                f.write(t, 'UTF-8')
                render("Contents file saved. Please open 'index.html' in " + ker.OperationController.getPath('slides.path') + " to see the slides.")
//                redirect(url: 'file://' + ker.OperationController.getPath('slides.path') + '/index.html')
//                redirect(url: "file:///D:/mhi/rps1/slides/index.html/")
            }
            else {
                render 'Setting slides.path is not set or path does not exists.'
            }
    }

    def kanban() {
        render(view: '/page/kanban', model: [])
    }

    def kanbanCrs() {
        render(view: '/page/kanbanCrs', model: [])
    }

    def mobile() {
        render(view: '/page/mobile', model: [mobileView: true])
    }

    def colors() {

    }

    def settingsMain(){
        render(template: '/page/settings', model: [full: false])

    }
    def settingsFull(){
        render(template: '/page/settings', model: [full: true])

    }
    def heartbeat(){
        render 'ok'
    }
	
	   def heartbeatJson(){
	     def builder = new JSONBuilder()
  def json = builder.build {
            result = "ok"
            
        }
        render(status: 200, contentType: 'application/json', text: json)
    }
	
	
    def importbeat(){

        def r = ''
        def c = 0
        def f = ker.OperationController.getPath('root.rps1.path')
        if (f) {
            new File(f)?.listFiles().each(){
                 if (it.isFile())
                     c++
                if (it.isDirectory() && it.name.length() > 1)
                    c++
            }
        }
        render '(' + c.toString() + ')'

    }
  def editHeartbeat(){

        def r = ''
        def c = 0
      def f = ker.OperationController.getPath('editBox.path')
      if (f) {
          new File(f)?.listFiles().each(){
              if (it.isFile() && it.name?.endsWith('.md'))
                 c++
//               if (it.isDirectory() && it.name.length() > 1)
//                    c++
          }
      }
        render c.toString()

    }

} // end of class